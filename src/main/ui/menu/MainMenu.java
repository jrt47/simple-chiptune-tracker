package ui.menu;

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
    private static final Dimension WINDOW_SIZE = new Dimension(300, 300);
    private static final Dimension BUTTON_SIZE = new Dimension(200, 40);
    private static final int SPACING = 10;
    private static final Font FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 20);

    private static final String JSON_STORE = "./data/tracker.json";

    private Tracker tracker;

    private CardLayout layout;
    private JPanel mainMenu;

    private JButton newButton;
    private JButton loadButton;
    private JButton deleteButton;
    private JButton quitButton;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public MainMenu() {
        super("Simple Chiptune Tracker");
        initializeFields();
        initializeGraphics();
        initializeInteraction();
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
        initializeCards();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(WINDOW_SIZE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeCards() {
        layout = new CardLayout();
        setLayout(layout);

        mainMenu =  new JPanel();
        initializeMainMenu();

        JPanel newMenu = new NewMenu();
        JPanel loadMenu = new LoadMenu();
        JPanel deleteMenu = new DeleteMenu();

        add(mainMenu);
        add(newMenu);
        add(loadMenu);
        add(deleteMenu);

        layout.addLayoutComponent(mainMenu, "main");
        layout.addLayoutComponent(newMenu, "new");
        layout.addLayoutComponent(loadMenu, "load");
        layout.addLayoutComponent(deleteMenu, "delete");
    }

    private void initializeMainMenu() {
        mainMenu.setLayout(new FlowLayout(FlowLayout.CENTER, SPACING, SPACING));

        JLabel title = new JLabel("Simple Chiptune Tracker");
        newButton = new JButton("New Track");
        loadButton = new JButton("Load Track");
        deleteButton = new JButton("Delete Track");
        quitButton = new JButton("Quit");

        title.setFont(TITLE_FONT);
        formatButton(newButton);
        formatButton(loadButton);
        formatButton(deleteButton);
        formatButton(quitButton);

        mainMenu.add(title);
        mainMenu.add(newButton);
        mainMenu.add(loadButton);
        mainMenu.add(deleteButton);
        mainMenu.add(quitButton);
    }

    private static void formatButton(JButton button) {
        button.setPreferredSize(BUTTON_SIZE);
        button.setFont(FONT);
    }

    private void initializeInteraction() {
        MainMenuListener listener = new MainMenuListener();
        newButton.addActionListener(listener);
        loadButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
        quitButton.addActionListener(listener);
    }

    private class MainMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (newButton.equals(source)) {
                layout.show(getContentPane(), "new");
            } else if (loadButton.equals(source)) {
                layout.show(getContentPane(), "load");
            } else if (deleteButton.equals(source)) {
                layout.show(getContentPane(), "delete");
            } else if (quitButton.equals(source)) {
                saveTracker();
                System.exit(0);
            }
        }
    }
}
