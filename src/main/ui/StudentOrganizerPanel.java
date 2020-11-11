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
// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
// and components-MenuDemoProject's components.MenuDemo
// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuDemoProject/src/components/MenuDemo.java
// Represents the Student Organizer's panel
public class StudentOrganizerPanel extends JPanel implements ListSelectionListener, ActionListener {
    private JList studentOrganizerList;
    private static JFrame frame;
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

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public StudentOrganizerPanel() {
        // based on code written in components-ListDemoProject's components.ListDemo ListDemo() constructor
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        super(new BorderLayout());

        init();

        studentOrganizerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentOrganizerList.addListSelectionListener(this);
        studentOrganizerList.setVisibleRowCount(5);
        JScrollPane studentOrganizerScrollPane = new JScrollPane(studentOrganizerList);

        markCompleteButton.setActionCommand(markCompleteString);
        markCompleteButton.addActionListener(new MarkCompleteListener());
        markCompleteButton.setEnabled(false);

        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new DeleteListener());
        deleteButton.setEnabled(false);

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
    // EFFECTS: initializes DefaultListModel, StudentOrganizer, JsonWriter, JsonReader, JList, markCompleteButton,
    //          and deleteButton objects
    private void init() {
        // based on code written in Teller's ui.TellerApp init() method https://github.students.cs.ubc.ca/CPSC210/TellerApp,
        // JsonSerializationDemo's ui.WorkRoomApp WorkRoomApp() constructor https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo,
        // and components-ListDemoProject's components.ListDemo ListDemo() constructor
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        studentOrganizerModel = new DefaultListModel();
        myStudentOrganizer = new StudentOrganizer();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        studentOrganizerList = new JList(studentOrganizerModel);
        markCompleteButton = new JButton(markCompleteString);
        deleteButton = new JButton(deleteString);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public void loadAssignments() {
        // based on code written in components-ListDemoProject's components.ListDemo ListDemo() constructor
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        if (studentOrganizerModel.getSize() > 0) {
            studentOrganizerModel.removeAllElements();
        }
        loadStudentOrganizer();
        for (Assignment a: myStudentOrganizer.viewAllAssignmentsByCourseCode()) {
            studentOrganizerModel.addElement(a);
        }
        studentOrganizerList.setSelectedIndex(0);
        frame.pack();
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private class MarkCompleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { // based on code written in components-ListDemoProject's
                                            // components.ListDemo FireListener class' actionPerformed method
            // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
            int index = studentOrganizerList.getSelectedIndex();
            Assignment assignment = (Assignment) studentOrganizerModel.getElementAt(index);
            assignment.markComplete();
            studentOrganizerList.updateUI();

            int size = studentOrganizerModel.getSize();

            if (size == 0) {
                markCompleteButton.setEnabled(false);
            } else {
                if (index == studentOrganizerModel.getSize()) {
                    index--;
                }
                studentOrganizerList.setSelectedIndex(index);
                studentOrganizerList.ensureIndexIsVisible(index);
            }
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { // based on code written in components-ListDemoProject's
                                            // components.ListDemo FireListener class' actionPerformed method
            // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
            int index = studentOrganizerList.getSelectedIndex();
            Assignment assignment = (Assignment) studentOrganizerModel.getElementAt(index);
            myStudentOrganizer.deleteAssignment(assignment.getName(), assignment.getCourseCode());
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

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    @Override
    public void valueChanged(ListSelectionEvent e) {
        // based on code written in components-ListDemoProject's components.ListDemo valueChanged method
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        if (e.getValueIsAdjusting() == false) {

            if (studentOrganizerList.getSelectedIndex() == -1) {
                markCompleteButton.setEnabled(false);
                deleteButton.setEnabled(false);
            } else {
                markCompleteButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }

        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public JMenuBar createMenuBar() {
        // based on code written in components-MenuDemoProject's components.MenuDemo createMenuBar() method
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuDemoProject/src/components/MenuDemo.java
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

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    @Override
    public void actionPerformed(ActionEvent e) {
        // based on code written in components-MenuDemoProject's components.MenuDemo actionPerformed method
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuDemoProject/src/components/MenuDemo.java
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
        // loadWorkRoom() method https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        try {
            myStudentOrganizer = jsonReader.read();
            System.out.printf("Loaded Student Organizer from %s\n", JSON_STORE);
        } catch (IOException e) {
            System.out.printf("Unable to read from file: %s\n", JSON_STORE);
        }
    }

    // EFFECTS: saves the Student Organizer to file
    private void saveStudentOrganizer() { // based on code written in JsonSerializationDemo's ui.WorkRoomApp
        // saveWorkRoom() method https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        try {
            jsonWriter.open();
            jsonWriter.write(myStudentOrganizer);
            jsonWriter.close();
            System.out.printf("Saved Student Organizer to %s\n", JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.printf("Unable to write to file: %s\n", JSON_STORE);
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    private static void createAndShowGUI() {
        // based on code in the createAndShowGUI() method as found in components-ListDemoProject's components.ListDemo
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        // and components-MenuDemoProject's components.MenuDemo
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuDemoProject/src/components/MenuDemo.java
        //Create and set up the window.
        frame = new JFrame("Student Organizer App");
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

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public static void main(String[] args) {
        // code extracted from components-ListDemoProject's components.ListDemo main method
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
