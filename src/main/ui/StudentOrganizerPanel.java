package ui;

import model.Assignment;
import model.StudentOrganizer;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// based on code written in components-ListDemoProject's components.ListDemo
// and components-MenuDemoProject's components.MenuDemo as found in
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// Represents the Student Organizer's panel
public class StudentOrganizerPanel extends JPanel implements ListSelectionListener, ActionListener {
    private JList studentOrganizerList;
    private DefaultListModel studentOrganizerModel;
    private StudentOrganizer myStudentOrganizer;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/studentorganizer.json";

    private static final String markCompleteString = "Mark Complete";
    private static final String deleteString = "Delete";
    private JButton markCompleteButton;
    private JButton deleteButton;

    private static final String loadData = "Load Data";
    private static final String saveData = "Save Data";

    public StudentOrganizerPanel() {
        super(new BorderLayout());

        init();

        studentOrganizerList = new JList(studentOrganizerModel);
        studentOrganizerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentOrganizerList.setSelectedIndex(0);
        studentOrganizerList.addListSelectionListener(this);
        studentOrganizerList.setVisibleRowCount(5);
        JScrollPane studentOrganizerScrollPane = new JScrollPane(studentOrganizerList);

        markCompleteButton = new JButton(markCompleteString);
        markCompleteButton.setActionCommand(markCompleteString);
        markCompleteButton.addActionListener(new MarkCompleteListener());

        deleteButton = new JButton(deleteString);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new DeleteListener());

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(markCompleteButton);
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(deleteButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(studentOrganizerScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: initializes DefaultListModel, StudentOrganizer, JsonWriter, and JsonReader objects
    private void init() { // based on code written in Teller's ui.TellerApp init() method
        studentOrganizerModel = new DefaultListModel();
        myStudentOrganizer = new StudentOrganizer();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    public void loadAssignments() {
        loadStudentOrganizer();
        for (Assignment a: myStudentOrganizer.viewAllAssignmentsByCourseCode()) {
            studentOrganizerModel.addElement(a);
        }
    }

    private class MarkCompleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = studentOrganizerList.getSelectedIndex();
            Assignment assignment = (Assignment) studentOrganizerModel.elementAt(index);
            assignment.markComplete();
        }
    }

    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = studentOrganizerList.getSelectedIndex();
            studentOrganizerModel.remove(index);

            int size = studentOrganizerModel.getSize();

            if (size == 0) {
                deleteButton.setEnabled(false);
            } else {
                if (index == studentOrganizerModel.getSize()) {
                    index--;
                }
                studentOrganizerList.setSelectedIndex(index);
                studentOrganizerList.ensureIndexIsVisible(index);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (studentOrganizerList.getSelectedIndex() == -1) {
                deleteButton.setEnabled(false);
            } else {
                deleteButton.setEnabled(true);
            }

        }
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_M);
        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program"); // remove if unnecessary
        menuBar.add(menu);

        menuItem = new JMenuItem(loadData, KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem(saveData, KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();
        switch (source.getText()) {
            case loadData:
                loadAssignments();
                break;
            case saveData:
                saveStudentOrganizer();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Student Organizer from file
    private void loadStudentOrganizer() { // based on code written in JsonSerializationDemo's ui.WorkRoomApp
        // loadWorkRoom() method
        try {
            myStudentOrganizer = jsonReader.read();
            System.out.printf("Loaded Student Organizer from %s\n", JSON_STORE);
        } catch (IOException e) {
            System.out.printf("Unable to read from file: %s\n", JSON_STORE);
        }
    }

    // EFFECTS: saves the Student Organizer to file
    private void saveStudentOrganizer() { // based on code written in JsonSerializationDemo's ui.WorkRoomApp
        // saveWorkRoom() method
        try {
            jsonWriter.open();
            jsonWriter.write(myStudentOrganizer);
            jsonWriter.close();
            System.out.printf("Saved Student Organizer to %s\n", JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.printf("Unable to write to file: %s\n", JSON_STORE);
        }
    }

    private static void createAndShowGUI() { // uses both List and Menu createAndShowGUI methods
        //Create and set up the window.
        JFrame frame = new JFrame("ListDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        StudentOrganizerPanel studentOrganizerPanel = new StudentOrganizerPanel();
        frame.setJMenuBar(studentOrganizerPanel.createMenuBar());
        JComponent newContentPane = studentOrganizerPanel;
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) { // uses List/Menu main method
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
