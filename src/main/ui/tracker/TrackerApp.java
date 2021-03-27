package ui.tracker;

import model.Track;
import ui.menu.MainMenu;
import ui.tracker.taskbar.EditMenu;
import ui.tracker.taskbar.FileMenu;
import ui.tracker.taskbar.HelpMenu;
import ui.tracker.taskbar.ToolBar;

import javax.swing.*;
import java.awt.*;

public class TrackerApp extends JFrame {
    public static final Font FONT = new Font("SansSerif", Font.PLAIN, 14);

    private Track track;
    private TrackEditor trackEditor;
    private MainMenu mainMenu;

    public TrackerApp(Track track, MainMenu mainMenu) {
        super(track.getName());
        this.track = track;
        this.mainMenu = mainMenu;
        initializeGraphics();
        // TODO
    }

    public Track getTrack() {
        return track;
    }

    public TrackEditor getTrackEditor() {
        return trackEditor;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    private void initializeGraphics() {
        initializeToolbar();
        trackEditor = new TrackEditor(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeToolbar() {
        JPanel taskBar = new JPanel(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();

        FileMenu fileMenu = new FileMenu(this);
        EditMenu editMenu = new EditMenu(this);
        HelpMenu helpMenu = new HelpMenu(this);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        ToolBar toolBar = new ToolBar(this);

        taskBar.add(menuBar, BorderLayout.NORTH);
        taskBar.add(toolBar, BorderLayout.SOUTH);

        add(taskBar, BorderLayout.NORTH);
    }

    public static void formatMenu(JMenu menu) {
        int width = menu.getPreferredSize().width;
        menu.setPreferredSize(new Dimension(width, ToolBar.COMPONENT_HEIGHT));
        menu.setFont(TrackerApp.FONT);
    }
}
