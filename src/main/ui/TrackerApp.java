package ui;

import model.Event;
import model.InstrumentChannel;
import model.Track;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// The music tracker application
public class TrackerApp {
    private static final String DIVIDER = "+--+--------+--------+--------+--------+";
    private static final int BARS_PER_PAGE = 2;
    private static final int ROWS_PER_PAGE = BARS_PER_PAGE * InstrumentChannel.ROWS_PER_BAR;

    private List<Track> trackList;
    private Track selectedTrack;
    private int selectedPage;
    private Scanner input;

    // EFFECTS: initializes and runs the tracker app
    public TrackerApp() {
        trackList = new ArrayList<>();
        input = new Scanner(System.in);
        runTracker();
    }

    // EFFECTS: continually runs the main menu of the tracker app
    private void runTracker() {
        while (true) {
            displayMainMenu();
            String nextInput = input.next();
            if (nextInput.equals("q")) {
                System.out.println("\nGoodbye!");
                break;
            } else {
                processMenuCommand(nextInput);
            }
        }
    }

    // EFFECTS: Print the main menu options
    private void displayMainMenu() {
        selectOne();
        System.out.println("\tNew track (n)");
        System.out.println("\tLoad track (l)");
        System.out.println("\tQuit (q)");
    }

    // EFFECTS: processes the main menu command
    private void processMenuCommand(String nextInput) {
        if (nextInput.equals("n")) {
            createNewTrack();
        } else if (nextInput.equals("l")) {
            loadTrack();
        } else {
            invalidInput();
        }
    }

    // EFFECTS: creates, stores, and opens a new track
    private void createNewTrack() {
        System.out.println("\nTrack name:");
        input.nextLine();
        String name = input.nextLine();
        Track newTrack = new Track(name);
        selectedTrack = newTrack;
        trackList.add(newTrack);
        openTrack();
    }

    // EFFECTS: allows the user to select and open one of the previously created tracks
    private void loadTrack() {
        System.out.println("\nPlease select a track:");
        for (Track track : trackList) {
            System.out.println("\t" + track.getName());
        }
        input.nextLine();
        String name = input.nextLine();
        boolean trackFound = false;
        for (Track track : trackList) {
            if (name.equals(track.getName())) {
                selectedTrack = track;
                trackFound = true;
                openTrack();
            }
        }
        if (!trackFound) {
            System.out.println("\nThat track does not exist.");
        }
    }

    // EFFECTS: Prints a selection message
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

    // EFFECTS: displays the previous page
    private void previousPage() {
        if (selectedPage > 1) {
            selectedPage--;
        }
    }

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

    // EFFECTS: displays the next page
    private void nextPage() {
        if (selectedPage < numberOfPages()) {
            selectedPage++;
        }
    }

    // EFFECTS: allows the user to rename the currently selected track
    private void rename() {
        System.out.println("\nName:");
        input.nextLine();
        String name = input.nextLine();
        selectedTrack.setName(name);
    }

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

    // EFFECTS: transposes the entire track up by an octave
    private void transposeTrackUpByOctave() {
        selectedTrack.transposeUpByOctave();
    }

    // EFFECTS: transposes the entire track down by an octave
    private void transposeTrackDownByOctave() {
        selectedTrack.transposeDownByOctave();
    }

    // EFFECTS: transposes the entire track by a given number of semitones
    private void transposeTrackSemitones() {
        System.out.println("\nSemitones: ");
        try {
            int semitones = input.nextInt();
            selectedTrack.transpose(semitones);
        } catch (InputMismatchException e) {
            invalidInput();
        }
    }

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

    private void displayTransposeTypeMenu() {
        selectOne();
        System.out.println("\tTranspose up by an octave (u)");
        System.out.println("\tTranspose down by an octave (d)");
        System.out.println("\tTranspose by a specific number of semitones (s)");
        goBack();
    }

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

    private void transposeChannelUpByOctave() {
        String channel = chooseChannel();
        if (channel.equals("b")) {
            transposeChannel();
        } else {
            selectedTrack.transposeUpByOctave(channel);
        }
    }

    private void transposeChannelDownByOctave() {
        String channel = chooseChannel();
        if (channel.equals("b")) {
            transposeChannel();
        } else {
            selectedTrack.transposeDownByOctave(channel);
        }
    }

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

    private void clear() {
        displayClearMenu();
        String nextInput = input.next();
        if (!nextInput.equals("b")) {
            processClearCommand(nextInput);
        }
    }

    private void displayClearMenu() {
        selectOne();
        System.out.println("\tClear a single note or rest (s)");
        System.out.println("\tClear a channel (c)");
        System.out.println("\tClear the track (t)");
        goBack();
    }

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

    private void clearChannel() {
        displayClearChannelMenu();
        String command = input.next();
        if (command.equals("b")) {
            clear();
        } else {
            processClearChannelCommand(command);
        }
    }

    private void clearTrack() {
        displayClearTrackMenu();
        String command = input.next();
        if (command.equals("b")) {
            clear();
        } else {
            processClearTrackCommand(command);
        }
    }

    private void displayClearTrackMenu() {
        selectOne();
        System.out.println("\tClear a section of the track (s)");
        System.out.println("\tClear the entire track (e)");
        goBack();
    }

    private void processClearTrackCommand(String command) {
        if (command.equals("s")) {
            clearTrackSection();
        } else if (command.equals("e")) {
            clearEntireTrack();
        } else {
            invalidInput();
        }
    }

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

    private void clearEntireTrack() {
        selectedTrack.clear();
    }

    private void displayClearChannelMenu() {
        selectOne();
        System.out.println("\tClear a section of the channel (s)");
        System.out.println("\tClear the entire channel (e)");
        goBack();
    }

    private void processClearChannelCommand(String command) {
        if (command.equals("s")) {
            clearChannelSection();
        } else if (command.equals("e")) {
            clearEntireChannel();
        } else {
            invalidInput();
        }
    }

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

    private void clearEntireChannel() {
        String channel = chooseChannel();
        if (channel.equals("b")) {
            clearChannel();
        } else {
            selectedTrack.clear(channel);
        }
    }

    private void addEffect() {
        displayEffectMenu();
        String effect = input.next();
        if (!effect.equals("b")) {
            processAddEffectCommand(effect);
        }
    }

    private void displayEffectMenu() {
        selectOne();
        System.out.println("\tStaccato (s)");
        System.out.println("\tRemove effect (r)");
        goBack();
    }

    private void processAddEffectCommand(String effect) {
        if (effect.equals("s")) {
            makeStaccato();
        } else if (effect.equals("r")) {
            makeNotStaccato();
        } else {
            invalidInput();
        }
    }

    private void makeStaccato() {
        String channel = chooseChannel();
        int row = chooseRow();
        boolean stop = channel.equals("b") || row == -1;
        if (!stop) {
            selectedTrack.makeStaccato(channel, row);
        }
    }

    private void makeNotStaccato() {
        String channel = chooseChannel();
        int row = chooseRow();
        boolean stop = channel.equals("b") || row == -1;
        if (!stop) {
            selectedTrack.makeNotStaccato(channel, row);
        }
    }

    private void addNoteOrRest() {
        displayAddNoteOrRestMenu();
        String nextInput = input.next();
        if (!nextInput.equals("b")) {
            processAddNoteOrRestCommand(nextInput);
        }
    }

    private void displayAddNoteOrRestMenu() {
        selectOne();
        System.out.println("\tAdd a note (n)");
        System.out.println("\tAdd a rest (r)");
        goBack();
    }

    private void processAddNoteOrRestCommand(String nextInput) {
        if (nextInput.equals("n")) {
            addNote();
        } else if (nextInput.equals("r")) {
            addRest();
        } else {
            invalidInput();
        }
    }

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

    private void addRest() {
        String channel = chooseChannel();
        if (!channel.equals("b")) {
            int row = chooseRow();
            if (!(row == -1)) {
                selectedTrack.addRest(channel, row + rowOffset());
            }
        }
    }

    private int chooseNote() {
        System.out.println("\nNote (ie. C, F#, A):");
        String note = input.next();
        return interpretNote(note);
    }

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
}
