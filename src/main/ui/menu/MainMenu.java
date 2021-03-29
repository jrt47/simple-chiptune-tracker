package ui.menu;

import model.Track;
import model.Tracker;
import persistence.JsonWriter;
import persistence.JsonReader;
import ui.tracker.TrackerApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the main menu for the tracker app
public class MainMenu extends JFrame {
    private static final Dimension WINDOW_SIZE = new Dimension(290, 250);
    public static final int SPACING = 10;
    private static final int MAIN_BUTTON_WIDTH = 200;
    private static final int SUB_BUTTON_WIDTH = (200 - SPACING) / 2;
    private static final Dimension MAIN_BUTTON_SIZE = new Dimension(MAIN_BUTTON_WIDTH, 40);
    private static final Dimension SUB_BUTTON_SIZE = new Dimension(SUB_BUTTON_WIDTH, 30);
    private static final Dimension TEXT_COMPONENT_SIZE = new Dimension(MAIN_BUTTON_WIDTH, 30);
    public static final Font FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 20);

    private static final String JSON_STORE = "./data/tracker.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Tracker tracker;

    private JPanel cardPanel;

    private JPanel mainMenu;
    private NewMenu newMenu;
    private LoadMenu loadMenu;

    private JButton newButton;
    private JButton loadButton;
    private JButton quitButton;

    // MODIFIES: this
    // EFFECTS: initializes and opens the main menu
    public MainMenu() {
        super("Simple Chiptune Tracker");
        initializeFields();
        initializeGraphics();
        initializeInteraction();
    }

    // EFFECTS: returns the tracker operated by the application
    public Tracker getTracker() {
        return tracker;
    }

    // MODIFIES: this
    // EFFECTS: initializes the tracker app fields
    private void initializeFields() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        loadTracker();
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
    // EFFECTS: initializes the main menu graphics
    private void initializeGraphics() {
        setLayout(new FlowLayout());
        addTitle();
        initializeCards();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setMinimumSize(WINDOW_SIZE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds the title of the application to the frame
    private void addTitle() {
        JLabel title = new JLabel("Simple Chiptune Tracker");
        title.setFont(TITLE_FONT);
        add(title);
    }

    // MODIFIES: this
    // EFFECTS: initializes the tracker menu cards
    private void initializeCards() {
        CardLayout layout = new CardLayout();
        cardPanel = new JPanel(layout);
        cardPanel.setPreferredSize(WINDOW_SIZE);

        mainMenu = new JPanel();
        initializeMainMenu();

        newMenu = new NewMenu(this);
        loadMenu = new LoadMenu(this);

        cardPanel.add(mainMenu);
        cardPanel.add(newMenu);
        cardPanel.add(loadMenu);

        layout.addLayoutComponent(mainMenu, "main");
        layout.addLayoutComponent(newMenu, "new");
        layout.addLayoutComponent(loadMenu, "load");

        add(cardPanel);
    }

    // MODIFIES: this
    // EFFECTS: initializes the main menu card
    private void initializeMainMenu() {
        mainMenu.setLayout(new FlowLayout(FlowLayout.CENTER, SPACING, SPACING));

        newButton = new JButton("New Track");
        loadButton = new JButton("Load Track");
        quitButton = new JButton("Quit");

        formatMainButton(newButton);
        formatMainButton(loadButton);
        formatMainButton(quitButton);

        mainMenu.add(newButton);
        mainMenu.add(loadButton);
        mainMenu.add(quitButton);
    }

    // MODIFIES: button
    // EFFECTS: formats the given button according to the main menu specifications
    private static void formatMainButton(JButton button) {
        button.setPreferredSize(MAIN_BUTTON_SIZE);
        button.setFont(FONT);
    }

    // MODIFIES: button
    // EFFECTS: formats the given button according to the sub-menu specifications
    public static void formatSubButton(JButton button) {
        button.setPreferredSize(SUB_BUTTON_SIZE);
        button.setFont(FONT);
    }

    // MODIFIES: textComponent
    // EFFECTS: formats the given text component according to the menu specifications
    public static void formatTextComponent(JComponent textComponent) {
        textComponent.setPreferredSize(TEXT_COMPONENT_SIZE);
        textComponent.setFont(FONT);
    }

    // MODIFIES: this
    // EFFECTS: initializes the interaction for the main menu buttons
    private void initializeInteraction() {
        MainMenuListener listener = new MainMenuListener();
        newButton.addActionListener(listener);
        loadButton.addActionListener(listener);
        quitButton.addActionListener(listener);
    }

    // MODIFIES: this
    // EFFECTS: opens the new menu card
    private void openNewMenu() {
        newMenu.resetTextField();
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        layout.show(cardPanel, "new");
    }

    // MODIFIES: this
    // EFFECTS: opens the load menu card
    private void openLoadMenu() {
        loadMenu.updateTracks();
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        layout.show(cardPanel, "load");
    }

    // EFFECTS: saves the tracker data and quits the application
    public void quit() {
        saveTracker();
        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS: opens the main menu from one of the sub-menus
    public void goBack() {
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        layout.show(cardPanel, "main");
    }

    // MODIFIES: this
    // EFFECTS: closes the menu and opens the tracker application
    public void openTrack(String name) {
        Track track = tracker.getTrack(name);
        if (!(track == null)) {
            setVisible(false);
            new TrackerApp(track, this);
        }
    }

    // action listener for main menu buttons
    private class MainMenuListener implements ActionListener {

        // MODIFIES: MainMenu.this
        // EFFECTS: performs the required operation when a button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (newButton.equals(source)) {
                openNewMenu();
            } else if (loadButton.equals(source)) {
                openLoadMenu();
            } else if (quitButton.equals(source)) {
                quit();
            }
        }
    }
}
