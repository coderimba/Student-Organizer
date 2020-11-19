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

    // MODIFIES: this
    // EFFECTS: creates the panel's border, sets the studentOrganizerList's selection model as single selection,
    // initializes studentOrganizerScrollPane, creates and disenables markComplete and delete buttons, initializes
    // button pane and adds the markComplete and delete buttons there. Centralizes studentOrganizerScrollPane and
    // places the button pane at the bottom of the panel. Adds appropriate listeners for studentOrganizerList,
    // markComplete, and delete buttons
    public StudentOrganizerPanel() {
        // based on code written in components-ListDemoProject's components.ListDemo ListDemo() constructor
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
        super(new BorderLayout());

        init();

        studentOrganizerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentOrganizerList.addListSelectionListener(this);
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

    // MODIFIES: this
    // EFFECTS: if studentOrganizerModel's size is greater than 0, remove all its elements. Adds all assignments from
    // myStudentOrganizer to studentOrganizerModel, arranged by course code. Sets studentOrganizerList to select first
    // element by default and view three more rows than the number of elements in studentOrganizerModel
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
        studentOrganizerList.setVisibleRowCount(myStudentOrganizer.size() + 3);
        frame.pack();
    }

    // MODIFIES: this
    // EFFECTS: marks the selected assignment as complete and shows the updated studentOrganizerList accordingly
    private class MarkCompleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { // based on code written in components-ListDemoProject's
                                            // components.ListDemo FireListener class' actionPerformed method
            // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
            beep();

            int index = studentOrganizerList.getSelectedIndex();
            Assignment assignment = (Assignment) studentOrganizerModel.getElementAt(index);
            assignment.markComplete();
            studentOrganizerList.updateUI();
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the selected assignment. If the studentOrganizerModel's size is zero as a result, disenable
    // delete and markComplete buttons. Else, if the deleted assignment occupied the last index, set the selected index
    // to one less than the index of the deleted assignment, or to the currently selected index if that is not the case
    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { // based on code written in components-ListDemoProject's
                                            // components.ListDemo FireListener class' actionPerformed method
            // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
            beep();

            int index = studentOrganizerList.getSelectedIndex();
            Assignment assignment = (Assignment) studentOrganizerModel.getElementAt(index);
            myStudentOrganizer.deleteAssignment(assignment.getName(), assignment.getCourseCode());
            studentOrganizerModel.remove(index);

            int size = studentOrganizerModel.getSize();

            if (size == 0) {
                deleteButton.setEnabled(false);
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

    // MODIFIES: this
    // EFFECTS: if no changes to values are being made to the studentOrganizerList, disenable the markComplete and
    // delete buttons if no item is being selected, otherwise enable the buttons
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

    // EFFECTS: creates a menu bar, a menu titled "Menu", and two menu items, namely "Load Data" and "Save Data"
    public JMenuBar createMenuBar() {
        // based on code written in components-MenuDemoProject's components.MenuDemo createMenuBar() method
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuDemoProject/src/components/MenuDemo.java
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_M);
        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program");
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

    // EFFECTS: calls loadAssignments() method when the "Load Data" menu item is triggered, or
    // calls saveStudentOrganizer() method when the "Save Data" menu item is triggered
    @Override
    public void actionPerformed(ActionEvent e) {
        // based on code written in components-MenuDemoProject's components.MenuDemo actionPerformed method
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuDemoProject/src/components/MenuDemo.java
        beep();
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

    // EFFECTS: plays a beep sound
    public void beep() { // code extracted from Beeper.java's actionPerformed method
        // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/events/BeeperProject/src/events/Beeper.java
        Toolkit.getDefaultToolkit().beep();
    }

    // MODIFIES: this
    // EFFECTS: initializes frame, titled "Student Organizer App". Creates a studentOrganizerPanel and adds the menu bar
    // and content pane such that they are in view
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

    // EFFECTS: runs StudentOrganizerPanel
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
