package ui.menu;

import model.Track;
import model.Tracker;
import persistence.JsonWriter;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMenu extends JFrame {
    private static final Dimension WINDOW_SIZE = new Dimension(290, 250);
    public static final int SPACING = 10;
    private static final int MAIN_BUTTON_WIDTH = 200;
    private static final int SUB_BUTTON_WIDTH = (200 - SPACING) / 2;
    private static final Dimension MAIN_BUTTON_SIZE = new Dimension(MAIN_BUTTON_WIDTH, 40);
    private static final Dimension SUB_BUTTON_SIZE = new Dimension(SUB_BUTTON_WIDTH, 30);
    private static final Dimension TEXT_BOX_SIZE = new Dimension(MAIN_BUTTON_WIDTH, 30);
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

    public MainMenu() {
        super("Simple Chiptune Tracker");
        initializeFields();
        initializeGraphics();
        initializeInteraction();
    }

    public Tracker getTracker() {
        return tracker;
    }

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

    private void initializeGraphics() {
        setLayout(new FlowLayout());
        addTitle();
        initializeCards();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(WINDOW_SIZE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addTitle() {
        JLabel title = new JLabel("Simple Chiptune Tracker");
        title.setFont(TITLE_FONT);
        add(title);
    }

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

    private static void formatMainButton(JButton button) {
        button.setPreferredSize(MAIN_BUTTON_SIZE);
        button.setFont(FONT);
    }

    public static void formatSubButton(JButton button) {
        button.setPreferredSize(SUB_BUTTON_SIZE);
        button.setFont(FONT);
    }

    public static void formatTextBox(JComponent textBox) {
        textBox.setPreferredSize(TEXT_BOX_SIZE);
        textBox.setFont(FONT);
    }

    private void initializeInteraction() {
        MainMenuListener listener = new MainMenuListener();
        newButton.addActionListener(listener);
        loadButton.addActionListener(listener);
        quitButton.addActionListener(listener);
    }

    private void openNewMenu() {
        newMenu.resetTextField();
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        layout.show(cardPanel, "new");
    }

    private void openLoadMenu() {
        loadMenu.updateTracks();
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        layout.show(cardPanel, "load");
    }

    private void quit() {
        saveTracker();
        System.exit(0);
    }

    public void goBack() {
        CardLayout layout = (CardLayout) cardPanel.getLayout();
        layout.show(cardPanel, "main");
    }

    public void openTrack(String name) {
        Track track = tracker.getTrack(name);
        // TODO
    }

    private class MainMenuListener implements ActionListener {

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
