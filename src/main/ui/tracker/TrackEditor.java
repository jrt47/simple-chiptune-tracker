package ui.tracker;

import model.Event;
import model.Track;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.EventObject;
import java.util.HashMap;

// represents the track editor component within the main tracker application
public class TrackEditor extends JTable {
    private static final String FONT_NAME = "Monospaced";
    private static final int FONT_SIZE = 16;
    private static final Font FONT = new Font(FONT_NAME, Font.PLAIN, FONT_SIZE);
    private static final Font BOLD_FONT = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
    private static final int COLUMN_WIDTH = 71;
    private static final HashMap<String, Event> STRING_TO_EVENT = makeStringToEvent();

    private TrackerApp trackerApp;

    // EFFECTS: constructs and initializes the track editor
    public TrackEditor(TrackerApp trackerApp) {
        this.trackerApp = trackerApp;
        setModel(new TrackEditorTableModel());
        formatTrackEditor();

        JScrollPane scrollPane = new JScrollPane(this);
        formatScrollPane(scrollPane);
        trackerApp.add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: notifies the track editor that the track data has been modified
    public void dataChanged() {
        int selectedRow = getSelectedRow();
        int selectedColumn = getSelectedColumn();
        ((AbstractTableModel) getModel()).fireTableDataChanged();
        setRowSelectionInterval(selectedRow, selectedRow);
        setColumnSelectionInterval(selectedColumn, selectedColumn);
    }

    // MODIFIES: this
    // EFFECTS: formats the track editor component
    private void formatTrackEditor() {
        setFillsViewportHeight(true);
        setFont(FONT);
        getTableHeader().setFont(TrackerApp.FONT);
        setShowHorizontalLines(false);
        setIntercellSpacing(new Dimension(5, 0));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setRowSelectionInterval(0, 0);
        setColumnSelectionInterval(0, 0);
        setRowHeight(20);
        getColumnModel().getColumn(0).setPreferredWidth(40);
        for (int i = 1; i < 5; i++) {
            getColumnModel().getColumn(i).setPreferredWidth(COLUMN_WIDTH);
        }
        formatCells();
    }

    // MODIFIES: this
    // EFFECTS: formats the track editor cells
    private void formatCells() {
        JTextField editorTextField = new JTextField("");
        editorTextField.setFont(FONT);
        setDefaultEditor(Object.class, new DefaultCellEditor(editorTextField));
        setDefaultRenderer(Object.class, new TrackEditorCellRenderer());
    }

    // MODIFIES: this
    // EFFECTS: formats the scroll pane that the track editor is contained within
    private void formatScrollPane(JScrollPane scrollPane) {
        int width = getPreferredSize().width;
        int height = getPreferredSize().height + getTableHeader().getPreferredSize().height + 4;
        scrollPane.setPreferredSize(new Dimension(width, height));
    }

    // this method is overridden to customize the cell editor text
    @Override
    public boolean editCellAt(int row, int column, EventObject e) {
        boolean result = super.editCellAt(row, column, e);
        setEditorText(row, column);
        return result;
    }

    // EFFECTS: returns the given string as an editor-compatible string
    private static String toEditorString(String string) {
        return string.replaceAll("[-. ]", "").toUpperCase();
    }

    // MODIFIES: this
    // EFFECTS: sets the cell editor text according to the data in the cell
    private void setEditorText(int row, int column) {
        Component editor = getEditorComponent();
        if (editor != null) {
            String text = toEditorString(getValueAt(row, column).toString());
            ((JTextField) editor).setText(text);
        }
    }

    // EFFECTS: returns a hashmap mapping editor strings to their corresponding events
    private static HashMap<String, Event> makeStringToEvent() {
        HashMap<String, Event> hashMap = new HashMap<>();

        Event blank = new Event();
        blank.clear();
        Event rest = new Event();
        rest.makeRest();

        hashMap.put("", blank);
        hashMap.put("STOP", rest);
        hashMap.put("S", rest);

        for (int i = 1; i <= Event.MAX_PITCH; i++) {
            Event note = new Event();
            Event staccatoNote = new Event();

            note.makeNote(i);
            staccatoNote.makeNote(i);

            note.makeNotStaccato();
            staccatoNote.makeStaccato();

            hashMap.put(toEditorString(note.toString()), note);
            hashMap.put(toEditorString(staccatoNote.toString()), staccatoNote);
        }

        return hashMap;
    }

    // MODIFIES: this
    // EFFECTS: sets the event at the given row and channel according to the given editor string
    private void setEventAt(String channel, int row, String string) {
        Track track = trackerApp.getTrack();
        Event event = STRING_TO_EVENT.get(toEditorString(string));
        if (event == null) {
            track.clear(channel, row);
            return;
        }
        String type = event.getType();
        switch (type) {
            case "blank":
                track.clear(channel, row);
                break;
            case "rest":
                track.addRest(channel, row);
                break;
            case "note":
                track.addNote(channel, row, event.getPitch());
                if (event.getIsStaccato()) {
                    track.makeStaccato(channel, row);
                } else {
                    track.makeNotStaccato(channel, row);
                }
                break;
        }
    }

    // a table model that uses the current track to display the correct data
    private class TrackEditorTableModel extends AbstractTableModel {

        // EFFECTS: see super
        @Override
        public int getRowCount() {
            return trackerApp.getTrack().numberOfRows();
        }

        // EFFECTS: see super
        @Override
        public int getColumnCount() {
            return 5;
        }

        // EFFECTS: see super
        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "Row";
                case 1:
                    return "Pulse 1";
                case 2:
                    return "Pulse 2";
                case 3:
                    return "Triangle";
                case 4:
                    return "Noise";
                default:
                    return null;
            }
        }

        // EFFECTS: see super
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Track track = trackerApp.getTrack();
            int rowNum = rowIndex + 1;
            switch (columnIndex) {
                case 0:
                    return String.format("%03d", rowIndex);
                case 1:
                    return track.getEvent("pulse1", rowNum);
                case 2:
                    return track.getEvent("pulse2", rowNum);
                case 3:
                    return track.getEvent("triangle", rowNum);
                case 4:
                    return track.getEvent("noise", rowNum);
                default:
                    return null;
            }
        }

        // EFFECTS: see super
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex != 0;
        }

        // MODIFIES: this
        // EFFECTS: see super
        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            String channel = null;
            switch (columnIndex) {
                case 1:
                    channel = "pulse1";
                    break;
                case 2:
                    channel = "pulse2";
                    break;
                case 3:
                    channel = "triangle";
                    break;
                case 4:
                    channel = "noise";
            }
            setEventAt(channel, rowIndex + 1, (String) value);
        }
    }

    // a cell renderer that makes the row number of the first row of each bar bold
    private class TrackEditorCellRenderer extends DefaultTableCellRenderer {

        // EFFECTS: see super
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (row % 16 == 0 && column == 0) {
                renderer.setFont(BOLD_FONT);
            }
            return renderer;
        }
    }
}
