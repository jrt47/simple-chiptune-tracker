package ui;

import model.Event;
import model.InstrumentChannel;
import model.Track;
import model.Tracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

// The music tracker application
public class TrackerConsole {
    private static final String DIVIDER = "+--+--------+--------+--------+--------+";
    private static final int BARS_PER_PAGE = 2;
    private static final int ROWS_PER_PAGE = BARS_PER_PAGE * InstrumentChannel.ROWS_PER_BAR;
    private static final String JSON_STORE = "./data/tracker.json";

    private Tracker tracker;
    private Track selectedTrack;
    private int selectedPage;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: initializes and runs the tracker app
    public TrackerConsole() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        loadTracker();
        input = new Scanner(System.in);
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: continually runs the main menu of the tracker app and allows the user to select a command
    private void runTracker() {
        while (true) {
            displayMainMenu();
            String nextInput = input.next();
            if (nextInput.equals("q")) {
                saveTracker();
                System.out.println("\nGoodbye!");
                break;
            } else {
                processMenuCommand(nextInput);
            }
        }
    }

    // EFFECTS: saves the tracker to file
    // (modelled after JsonSerializationDemo repository)
    private void saveTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(tracker);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads tracker from file
    // (modelled after JsonSerializationDemo repository)
    private void loadTracker() {
        try {
            tracker = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: Print the main menu options
    private void displayMainMenu() {
        selectOne();
        System.out.println("\tNew track (n)");
        System.out.println("\tLoad track (l)");
        System.out.println("\tDelete track (d)");
        System.out.println("\tQuit (q)");
    }

    // MODIFIES: this
    // EFFECTS: processes the main menu command
    private void processMenuCommand(String nextInput) {
        switch (nextInput) {
            case "n":
                createNewTrack();
                break;
            case "l":
                loadTrack();
                break;
            case "d":
                deleteTrack();
                break;
            default:
                invalidInput();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to select and delete one of the previously created tracks saved to file
    private void deleteTrack() {
        if (tracker.numberOfTracks() == 0) {
            System.out.println("\nThere are no saved tracks to delete.");
        } else {
            System.out.println("\nPlease select a track to delete:");
            for (int i = 1; i <= tracker.numberOfTracks(); i++) {
                System.out.println("\t[" + i + "] " + tracker.get(i).getName());
            }
            try {
                int trackNum = input.nextInt();
                if (trackNum < 1 || trackNum > tracker.numberOfTracks()) {
                    invalidInput();
                } else {
                    tracker.remove(trackNum);
                }
            } catch (InputMismatchException e) {
                invalidInput();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates, stores, and opens a new track
    private void createNewTrack() {
        System.out.println("\nTrack name:");
        input.nextLine();
        String name = input.nextLine();
        Track newTrack = new Track(name);
        selectedTrack = newTrack;
        tracker.add(newTrack);
        openTrack();
    }

    // MODIFIES: this
    // EFFECTS: allows the user to select and open one of the previously created tracks saved to file
    private void loadTrack() {
        if (tracker.numberOfTracks() == 0) {
            System.out.println("\nThere are no saved tracks.");
        } else {
            System.out.println("\nPlease select a track:");
            for (int i = 1; i <= tracker.numberOfTracks(); i++) {
                System.out.println("\t[" + i + "] " + tracker.get(i).getName());
            }
            try {
                int trackNum = input.nextInt();
                if (trackNum < 1 || trackNum > tracker.numberOfTracks()) {
                    invalidInput();
                } else {
                    selectedTrack = tracker.get(trackNum);
                    openTrack();
                }
            } catch (InputMismatchException e) {
                invalidInput();
            }
        }
    }

    // EFFECTS: prints a selection message
    private void selectOne() {
        System.out.println("\nPlease select one:");
    }

    // EFFECTS: prints a go back options
    private void goBack() {
        System.out.println("\tGo back (b)");
    }

    // EFFECTS: prints an invalid input method
    private static void invalidInput() {
        System.out.println("\nThat input is invalid.");
    }

    // MODIFIES: this
    // EFFECTS: allows the user to select from the four channels in the selected track
    //          and returns the channel as a string, if no track is selected return "b"
    private String chooseChannel() {
        displayChannelMenu();
        String channel = input.next();
        switch (channel) {
            case "p1":
                return "pulse1";
            case "p2":
                return "pulse2";
            case "t":
                return "triangle";
            case "n":
                return "noise";
            case "b":
                return "b";
            default:
                invalidInput();
                return "b";
        }
    }

    // EFFECTS: displays options for channel selection
    private void displayChannelMenu() {
        System.out.println("\nPlease select a channel:");
        System.out.println("\tPulse 1 (p1)");
        System.out.println("\tPulse 2 (p2)");
        System.out.println("\tTriangle (t)");
        System.out.println("\tNoise (n)");
        System.out.println("\tGo back (b)");
    }

    // EFFECTS: allows the user to choose a valid row and returns the row
    //          return -1 if no valid row is selected
    private int chooseRow() {
        System.out.println("\nRow:");
        try {
            int row = input.nextInt();
            if (row < 1 || row > ROWS_PER_PAGE) {
                invalidInput();
                return -1;
            } else {
                return row;
            }
        } catch (InputMismatchException e) {
            invalidInput();
            return -1;
        }
    }

    // MODIFIES: this
    // EFFECTS: opens the currently selected track
    private void openTrack() {
        selectedPage = 1;
        while (true) {
            displayTrack();
            displayTrackMenu();
            String nextInput = input.next();
            if (nextInput.equals("q")) {
                break;
            } else {
                processTrackCommand(nextInput);
            }
        }
    }

    // EFFECTS: prints the information in the currently selected track
    private void displayTrack() {
        System.out.println("\nName: " + selectedTrack.getName());
        System.out.println("Tempo: " + selectedTrack.getTempo() + " BPM");
        displayTrackChannels();
    }

    // EFFECTS: prints the channels in the track
    private void displayTrackChannels() {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println("|  |Pulse 1 |Pulse 2 |Triangle| Noise  |");
        System.out.println(DIVIDER);
        displayChannelData();
        System.out.println(DIVIDER);
        System.out.println("\nPage: " + selectedPage + "/" + numberOfPages());
    }

    // EFFECTS: prints the data in each of the channels of the currently selected track
    private void displayChannelData() {
        for (int i = 1; i <= InstrumentChannel.ROWS_PER_BAR; i++) {
            displayTrackRow(i);
        }
        int numBarsRemaining = selectedTrack.numberOfBars() - (selectedPage - 1) * BARS_PER_PAGE;
        int numBarsRemainingOnPage = Math.min(numBarsRemaining, BARS_PER_PAGE);
        for (int i = 1; i < numBarsRemainingOnPage; i++) {
            displayNextBar(i);
        }
    }

    // EFFECTS: returns the row offset based on the currently selected page
    private int rowOffset() {
        return ROWS_PER_PAGE * (selectedPage - 1);
    }

    // EFFECTS: prints the next bars
    private void displayNextBar(int nextBarNum) {
        System.out.println(DIVIDER);
        int startRow = InstrumentChannel.ROWS_PER_BAR * nextBarNum + 1;
        int endRow = InstrumentChannel.ROWS_PER_BAR * (nextBarNum + 1);
        for (int i = startRow; i <= endRow; i++) {
            displayTrackRow(i);
        }
    }

    // REQUIRES: 1 <= rowNum <= ROWS_PER_PAGE
    // EFFECTS: prints a row of events in the currently selected track
    private void displayTrackRow(int rowNum) {
        int rowOffset = rowOffset();
        System.out.print("|" + rowNumToString(rowNum));
        System.out.print("| " + selectedTrack.getEvent("pulse1", rowOffset + rowNum));
        System.out.print(" | " + selectedTrack.getEvent("pulse2", rowOffset + rowNum));
        System.out.print(" | " + selectedTrack.getEvent("triangle", rowOffset + rowNum));
        System.out.println(" | " + selectedTrack.getEvent("noise", rowOffset + rowNum) + " |");
    }

    // REQUIRES: 1 <= rowNum <= 99
    // EFFECTS: converts the given number to a 2 character long string
    private static String rowNumToString(int rowNum) {
        if (rowNum <= 9) {
            return rowNum + " ";
        } else {
            return rowNum + "";
        }
    }

    // EFFECTS: returns the number of pages the currently selected track can be displayed in
    private int numberOfPages() {
        return (selectedTrack.numberOfBars() - 1) / BARS_PER_PAGE + 1;
    }

    // EFFECTS: print the track menu options
    private void displayTrackMenu() {
        selectOne();
        System.out.println("\tAdd a note or a rest to the track (a)");
        System.out.println("\tAdd or remove an effect from a note (e)");
        System.out.println("\tClear a part of the track (c)");
        System.out.println("\tTranspose a part of the track (t)");
        System.out.println("\tAdd or remove bars from the track (b)");
        System.out.println("\tChange the tempo of the track (s)");
        System.out.println("\tRename the track (r)");
        System.out.println("\tView the next page (n)");
        System.out.println("\tView the previous page (p)");
        System.out.println("\tSave and quit to menu (q)");
    }

    // MODIFIES: this
    // EFFECTS: processes the track menu command
    private void processTrackCommand(String nextInput) {
        if ("a".equals(nextInput)) {
            addNoteOrRest();
        } else if ("e".equals(nextInput)) {
            addEffect();
        } else if ("c".equals(nextInput)) {
            clear();
        } else if ("t".equals(nextInput)) {
            transpose();
        } else if ("b".equals(nextInput)) {
            addOrRemoveBars();
        } else if ("s".equals(nextInput)) {
            changeTempo();
        } else if ("r".equals(nextInput)) {
            rename();
        } else if ("n".equals(nextInput)) {
            nextPage();
        } else if ("p".equals(nextInput)) {
            previousPage();
        } else {
            invalidInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the previous page
    private void previousPage() {
        if (selectedPage > 1) {
            selectedPage--;
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to add a number of bars to the track
    private void addOrRemoveBars() {
        displayAddOrRemoveBarsMenu();
        String command = input.next();
        if (!command.equals("b")) {
            processAddOrRemoveBarsCommand(command);
        }
    }

    // EFFECTS: displays the add or remove bars menu
    private void displayAddOrRemoveBarsMenu() {
        selectOne();
        System.out.println("\tAdd bars (a)");
        System.out.println("\tRemove bars (r)");
        goBack();
    }

    // MODIFIES: this
    // EFFECTS: processes the add or remove bars command
    private void processAddOrRemoveBarsCommand(String command) {
        if (command.equals("a")) {
            addBars();
        } else if (command.equals("r")) {
            removeBars();
        } else {
            invalidInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to add a specific number of bars to the track
    private void addBars() {
        System.out.println("Number of bars:");
        try {
            int numBars = input.nextInt();
            if (numBars < 1) {
                invalidInput();
            } else {
                selectedTrack.addBars(numBars);
            }
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to remove a specific number of bars from the track
    private void removeBars() {
        System.out.println("Number of bars:");
        try {
            int numBars = input.nextInt();
            if (numBars < 1 || numBars >= selectedTrack.numberOfBars()) {
                invalidInput();
            } else {
                selectedTrack.removeBars(numBars);
                selectedPage = Math.min(selectedPage, numberOfPages());
            }
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the next page
    private void nextPage() {
        if (selectedPage < numberOfPages()) {
            selectedPage++;
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to rename the currently selected track
    private void rename() {
        System.out.println("\nName:");
        input.nextLine();
        String name = input.nextLine();
        selectedTrack.setName(name);
    }

    // MODIFIES: this
    // EFFECTS: allows the user to change the tempo of the currently selected track
    private void changeTempo() {
        System.out.println("\nTempo (BPM):");
        try {
            int tempo = input.nextInt();
            if (tempo <= 0) {
                invalidInput();
            } else {
                selectedTrack.setTempo(tempo);
            }
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to transpose a selection of the track
    private void transpose() {
        displayTransposeMenu();
        String command = input.next();
        if (!command.equals("b")) {
            processTransposeCommand(command);
        }
    }

    // EFFECTS: displays the transpose menu
    private void displayTransposeMenu() {
        selectOne();
        System.out.println("\tTranspose a channel (c)");
        System.out.println("\tTranspose the entire track (t)");
        goBack();
    }

    // MODIFIES: this
    // EFFECTS: processes the transpose command
    private void processTransposeCommand(String command) {
        if (command.equals("c")) {
            transposeChannel();
        } else if (command.equals("t")) {
            transposeTrack();
        } else {
            invalidInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to transpose a selection of the track
    private void transposeTrack() {
        displayTransposeTypeMenu();
        String command = input.next();
        if (command.equals("b")) {
            transpose();
        } else {
            processTransposeTrackCommand(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes the transpose track command
    private void processTransposeTrackCommand(String command) {
        switch (command) {
            case "u":
                transposeTrackUpByOctave();
                break;
            case "d":
                transposeTrackDownByOctave();
                break;
            case "s":
                transposeTrackSemitones();
                break;
            default:
                invalidInput();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: transposes the entire track up by an octave
    private void transposeTrackUpByOctave() {
        selectedTrack.transposeUpByOctave();
    }

    // MODIFIES: this
    // EFFECTS: transposes the entire track down by an octave
    private void transposeTrackDownByOctave() {
        selectedTrack.transposeDownByOctave();
    }

    // MODIFIES: this
    // EFFECTS: transposes the entire track by a given number of semitones
    private void transposeTrackSemitones() {
        System.out.println("\nSemitones (+ to transpose up, - to transpose down): ");
        try {
            int semitones = input.nextInt();
            selectedTrack.transpose(semitones);
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to transpose a specific channel
    private void transposeChannel() {
        displayTransposeTypeMenu();
        String command = input.next();
        if (command.equals("b")) {
            transpose();
        } else {
            processTransposeChannelCommand(command);
        }
    }

    // EFFECTS: displays the transpose type menu
    private void displayTransposeTypeMenu() {
        selectOne();
        System.out.println("\tTranspose up by an octave (u)");
        System.out.println("\tTranspose down by an octave (d)");
        System.out.println("\tTranspose by a specific number of semitones (s)");
        goBack();
    }

    // MODIFIES: this
    // EFFECTS: processes the transpose channel command
    private void processTransposeChannelCommand(String command) {
        switch (command) {
            case "u":
                transposeChannelUpByOctave();
                break;
            case "d":
                transposeChannelDownByOctave();
                break;
            case "s":
                transposeChannelSemitones();
                break;
            default:
                invalidInput();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: transposes the selected channel up by an octave in the selected track
    private void transposeChannelUpByOctave() {
        String channel = chooseChannel();
        if (channel.equals("b")) {
            transposeChannel();
        } else {
            selectedTrack.transposeUpByOctave(channel);
        }
    }

    // MODIFIES: this
    // EFFECTS: transposes the selected channel down by an octave in the selected track
    private void transposeChannelDownByOctave() {
        String channel = chooseChannel();
        if (channel.equals("b")) {
            transposeChannel();
        } else {
            selectedTrack.transposeDownByOctave(channel);
        }
    }

    // MODIFIES: this
    // EFFECTS: transposes the selected channel by a specified number of semitones in the selected track
    private void transposeChannelSemitones() {
        String channel = chooseChannel();
        if (channel.equals("b")) {
            transposeChannel();
        } else {
            System.out.println("\nSemitones: ");
            try {
                int semitones = input.nextInt();
                selectedTrack.transpose(channel, semitones);
            } catch (InputMismatchException e) {
                invalidInput();
                transposeChannel();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to clear a specified section of the selected track
    private void clear() {
        displayClearMenu();
        String nextInput = input.next();
        if (!nextInput.equals("b")) {
            processClearCommand(nextInput);
        }
    }

    // EFFECTS: displays the clear menu
    private void displayClearMenu() {
        selectOne();
        System.out.println("\tClear a single note or rest (s)");
        System.out.println("\tClear a channel (c)");
        System.out.println("\tClear the track (t)");
        goBack();
    }

    // MODIFIES: this
    // EFFECTS: processes the clear command
    private void processClearCommand(String command) {
        switch (command) {
            case "s":
                clearSingle();
                break;
            case "c":
                clearChannel();
                break;
            case "t":
                clearTrack();
                break;
            default:
                invalidInput();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to clear a specific event in the selected track
    private void clearSingle() {
        String channel = chooseChannel();
        if (channel.equals("b")) {
            clear();
        } else {
            int row = chooseRow();
            if (!(row == -1)) {
                selectedTrack.clear(channel, row);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to clear a channel in the selected track
    private void clearChannel() {
        displayClearChannelMenu();
        String command = input.next();
        if (command.equals("b")) {
            clear();
        } else {
            processClearChannelCommand(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to clear the entire selected track
    private void clearTrack() {
        displayClearTrackMenu();
        String command = input.next();
        if (command.equals("b")) {
            clear();
        } else {
            processClearTrackCommand(command);
        }
    }

    // EFFECTS: displays the clear track menu
    private void displayClearTrackMenu() {
        selectOne();
        System.out.println("\tClear a section of the track (s)");
        System.out.println("\tClear the entire track (e)");
        goBack();
    }

    // MODIFIES: this
    // EFFECTS: processes the clear track command
    private void processClearTrackCommand(String command) {
        if (command.equals("s")) {
            clearTrackSection();
        } else if (command.equals("e")) {
            clearEntireTrack();
        } else {
            invalidInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to clear a section of the track
    private void clearTrackSection() {
        System.out.println("\nFrom row:");
        try {
            int startRow = input.nextInt();
            if (startRow < 1 || startRow > ROWS_PER_PAGE) {
                invalidInput();
            }
            System.out.println("\nTo:");
            int endRow = input.nextInt();
            if (endRow < 1 || endRow > ROWS_PER_PAGE || endRow <= startRow) {
                invalidInput();
            }
            selectedTrack.clear(startRow, endRow);
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: clears the entire selected track
    private void clearEntireTrack() {
        selectedTrack.clear();
    }

    // EFFECTS: displays the clear channel menu
    private void displayClearChannelMenu() {
        selectOne();
        System.out.println("\tClear a section of the channel (s)");
        System.out.println("\tClear the entire channel (e)");
        goBack();
    }

    // MODIFIES: this
    // EFFECTS: processes the clear channel command
    private void processClearChannelCommand(String command) {
        if (command.equals("s")) {
            clearChannelSection();
        } else if (command.equals("e")) {
            clearEntireChannel();
        } else {
            invalidInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to clear a section of a specified channel in the selected track
    private void clearChannelSection() {
        String channel = chooseChannel();
        if (channel.equals("b")) {
            clearChannel();
        } else {
            try {
                System.out.println("\nFrom row:");
                int startRow = input.nextInt();
                if (startRow < 1 || startRow > ROWS_PER_PAGE) {
                    invalidInput();
                }
                System.out.println("\nTo:");
                int endRow = input.nextInt();
                if (endRow < 1 || endRow > ROWS_PER_PAGE || endRow <= startRow) {
                    invalidInput();
                }
                selectedTrack.clear(channel, startRow, endRow);
            } catch (InputMismatchException e) {
                invalidInput();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to clear an entire channel in the selected track
    private void clearEntireChannel() {
        String channel = chooseChannel();
        if (channel.equals("b")) {
            clearChannel();
        } else {
            selectedTrack.clear(channel);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to add an effect to a note in the selected track
    private void addEffect() {
        displayEffectMenu();
        String effect = input.next();
        if (!effect.equals("b")) {
            processAddEffectCommand(effect);
        }
    }

    // EFFECTS: displays the effect menu
    private void displayEffectMenu() {
        selectOne();
        System.out.println("\tStaccato (s)");
        System.out.println("\tRemove effect (r)");
        goBack();
    }

    // MODIFIES: this
    // EFFECTS: processes the add effect command
    private void processAddEffectCommand(String effect) {
        if (effect.equals("s")) {
            makeStaccato();
        } else if (effect.equals("r")) {
            makeNotStaccato();
        } else {
            invalidInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to make a specified note in the selected track staccato
    private void makeStaccato() {
        String channel = chooseChannel();
        int row = chooseRow();
        boolean stop = channel.equals("b") || row == -1;
        if (!stop) {
            selectedTrack.makeStaccato(channel, row);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to remove an effect from a specified note in the selected track
    private void makeNotStaccato() {
        String channel = chooseChannel();
        int row = chooseRow();
        boolean stop = channel.equals("b") || row == -1;
        if (!stop) {
            selectedTrack.makeNotStaccato(channel, row);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to add a note or a rest to the selected track
    private void addNoteOrRest() {
        displayAddNoteOrRestMenu();
        String nextInput = input.next();
        if (!nextInput.equals("b")) {
            processAddNoteOrRestCommand(nextInput);
        }
    }

    // EFFECTS: displays the add note or rest menu
    private void displayAddNoteOrRestMenu() {
        selectOne();
        System.out.println("\tAdd a note (n)");
        System.out.println("\tAdd a rest (r)");
        goBack();
    }

    // MODIFIES: this
    // EFFECTS: processes the add note or rest command
    private void processAddNoteOrRestCommand(String nextInput) {
        if (nextInput.equals("n")) {
            addNote();
        } else if (nextInput.equals("r")) {
            addRest();
        } else {
            invalidInput();
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to add a note to a specified position in the selected track
    private void addNote() {
        String channel = chooseChannel();
        if (!channel.equals("b")) {
            int row = chooseRow();
            if (!(row == -1)) {
                int relPitch = chooseNote();
                if (!(relPitch == -1)) {
                    int octave = chooseOctave();
                    if (!(octave == -1)) {
                        selectedTrack.addNote(channel, row + rowOffset(), relPitch + (octave - 1) * 12);
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the user to add a rest to a specified position in the selected track
    private void addRest() {
        String channel = chooseChannel();
        if (!channel.equals("b")) {
            int row = chooseRow();
            if (!(row == -1)) {
                selectedTrack.addRest(channel, row + rowOffset());
            }
        }
    }

    // EFFECTS: allows the user to choose a note from all possible notes
    private int chooseNote() {
        System.out.println("\nNote (ie. C, F#, A):");
        String note = input.next();
        return interpretNote(note);
    }

    // EFFECTS: allows the user to choose an octave from all possible octaves
    private int chooseOctave() {
        System.out.println("\nOctave (1, 2, or 3):");
        try {
            int octave = input.nextInt();
            if (octave < 1 || octave > Event.NUM_OCTAVES) {
                invalidInput();
                return -1;
            } else {
                return octave;
            }
        } catch (InputMismatchException e) {
            invalidInput();
            return -1;
        }
    }

    // EFFECTS: returns the integer that represents the given note
    private static int interpretNote(String note) {
        switch (note) {
            case "C":
                return 1;
            case "C#":
                return 2;
            case "D":
                return 3;
            case "D#":
                return 4;
            case "E":
                return 5;
            case "F":
                return 6;
            default:
                return interpretNoteFSharpToB(note);
        }
    }

    // REQUIRES: note is not C, C#, D, D#, E, or F
    // EFFECTS: returns the integer that represents the given note
    private static int interpretNoteFSharpToB(String note) {
        switch (note) {
            case "F#":
                return 7;
            case "G":
                return 8;
            case "G#":
                return 9;
            case "A":
                return 10;
            case "A#":
                return 11;
            case "B":
                return 12;
            default:
                invalidInput();
                return -1;
        }
    }

    public static void main(String[] args) {
        new TrackerConsole();
    }
}
