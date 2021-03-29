package ui.tracker.menubar;

import ui.tracker.TrackerApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents the tracker application help menu in the menu bar
public class HelpMenu extends JMenu {
    private static final String HELP_MESSAGE =
            "To add a note, enter a pitch (e.g. 'C', 'F#', 'A') followed by an octave ('1', '2', or '3')."
                    + "\nOptionally, add an 'S' to the end to make the note staccato."
                    + "\n\nTo add a rest, enter 'S' or 'STOP'."
                    + "\n\nCurrently, only naturals and sharps are supported by the track editor. Please use sharps"
                    + "\ninstead of flats for accidentals (e.g. use G# instead of Ab)."
                    + "\n\nSharps are not accepted in places where naturals can be used (e.g. use C instead of B#)."
                    + "\n\nNote: All text entries are NOT case-sensitive.";

    private TrackerApp trackerApp;
    private JMenuItem helpButton;

    // EFFECTS: constructs and initializes the help menu
    public HelpMenu(TrackerApp trackerApp) {
        super("Help");
        this.trackerApp = trackerApp;
        initializeGraphics();
        initializeInteraction();
    }

    // MODIFIES: this
    // EFFECTS: initializes the help menu graphics
    private void initializeGraphics() {
        TrackerApp.formatMenu(this);

        helpButton = new JMenuItem("Get Help");
        helpButton.setFont(TrackerApp.FONT);

        add(helpButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes interaction for the help menu components
    private void initializeInteraction() {
        HelpMenuListener listener = new HelpMenuListener();
        helpButton.addActionListener(listener);
    }

    // EFFECTS: displays a dialog box with instructions on how to operate the track editor
    private void getHelp() {
        JOptionPane.showMessageDialog(trackerApp, HELP_MESSAGE, "Help", JOptionPane.PLAIN_MESSAGE, null);
    }

    // an action listener for the help menu components
    private class HelpMenuListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: opens the help dialog box when the get help button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source.equals(helpButton)) {
                getHelp();
            }
        }
    }
}
