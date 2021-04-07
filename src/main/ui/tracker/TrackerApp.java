package ui.tracker;

import model.Track;
import ui.menu.MainMenu;
import ui.sound.TrackPlayer;
import ui.tracker.menubar.EditMenu;
import ui.tracker.menubar.FileMenu;
import ui.tracker.menubar.HelpMenu;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

// represents the main tracker application where track can be edited
public class TrackerApp extends JFrame {
    public static final Font FONT = new FontUIResource("SansSerif", Font.PLAIN, 14);

    private final Track track;
    private ToolBar toolBar;
    private TrackEditor trackEditor;
    private final TrackPlayer trackPlayer;
    private final MainMenu mainMenu;

    // EFFECTS: constructs and initializes the main tracker application
    public TrackerApp(Track track, MainMenu mainMenu) {
        super(track.getName());
        this.track = track;
        this.mainMenu = mainMenu;
        initializeComponents();
        trackPlayer = new TrackPlayer(this);
    }

    // EFFECTS: returns the track being operated by the tracker app
    public Track getTrack() {
        return track;
    }

    // EFFECTS: return's the tracker's toolbar component
    public ToolBar getToolBar() {
        return toolBar;
    }

    // EFFECTS: returns the tracker's track editor component
    public TrackEditor getTrackEditor() {
        return trackEditor;
    }

    // EFFECTS: returns the tracker's track player component
    public TrackPlayer getTrackPlayer() {
        return trackPlayer;
    }

    // EFFECTS: returns the main menu for the tracker app
    public MainMenu getMainMenu() {
        return mainMenu;
    }

    // MODIFIES: this
    // EFFECTS: initializes the tracker app components
    private void initializeComponents() {
        initializeTaskBar();
        trackEditor = new TrackEditor(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setUIFont();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the tracker app task bar which includes the menu bar and the tool bar
    private void initializeTaskBar() {
        JPanel taskBar = new JPanel(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();

        FileMenu fileMenu = new FileMenu(this);
        EditMenu editMenu = new EditMenu(this);
        HelpMenu helpMenu = new HelpMenu(this);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        toolBar = new ToolBar(this);

        taskBar.add(menuBar, BorderLayout.NORTH);
        taskBar.add(toolBar, BorderLayout.SOUTH);

        add(taskBar, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: sets the default font for all tracker app components
    private void setUIFont() {
        UIManager.put("Button.font", FONT);
        UIManager.put("ComboBox.font", FONT);
        UIManager.put("Label.font", FONT);
        UIManager.put("MenuBar.font", FONT);
        UIManager.put("MenuItem.font", FONT);
        UIManager.put("Menu.font", FONT);
        UIManager.put("OptionPane.font", FONT);
        UIManager.put("Panel.font", FONT);
        UIManager.put("TextField.font", FONT);
        UIManager.put("CheckBox.font", FONT);
        UIManager.put("RadioButton.font", FONT);
    }

    // MODIFIES: menu
    // EFFECTS: formats the given menu according to the taskbar defaults
    public static void formatMenu(JMenu menu) {
        int width = menu.getPreferredSize().width;
        menu.setPreferredSize(new Dimension(width, ToolBar.COMPONENT_HEIGHT));
        menu.setFont(TrackerApp.FONT);
    }
}
