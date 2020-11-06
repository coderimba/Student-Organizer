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
import java.io.IOException;

// based on code written in components-ListDemoProject's components.ListDemo
// as found in https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// Represents the Student Organizer's panel
public class StudentOrganizerPanel extends JPanel implements ListSelectionListener {
    private JList studentOrganizerList;
    private DefaultListModel studentOrganizerModel;
    private StudentOrganizer myStudentOrganizer;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/studentorganizer.json";

    private static final String markCompleteString = "Mark Complete";
    private static final String viewIncompleteString = "View Incomplete";
    private JButton markCompleteButton;
    private JButton viewIncompleteButton;

    public StudentOrganizerPanel() {
        super(new BorderLayout());

        studentOrganizerModel = new DefaultListModel();
        loadAssignments();

        studentOrganizerList = new JList(studentOrganizerModel);
        studentOrganizerList.setSelectionModel(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        studentOrganizerList.setSelectedIndex(0);
        studentOrganizerList.addListSelectionListener(this);
        studentOrganizerList.setVisibleRowCount(5);
        JScrollPane studentOrganizerScrollPane = new JScrollPane(studentOrganizerList);

        markCompleteButton = new JButton(markCompleteString);
        markCompleteButton.setActionCommand(markCompleteString);
        markCompleteButton.addActionListener(new MarkCompleteListener());

        viewIncompleteButton = new JButton(viewIncompleteString);
        viewIncompleteButton.setActionCommand(viewIncompleteString);
        viewIncompleteButton.addActionListener(new ViewIncompleteListener());

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(markCompleteButton);
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createVerticalStrut(5));
        buttonPane.add(viewIncompleteButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(studentOrganizerScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    public void loadAssignments() {
        loadStudentOrganizer();
        for (Assignment a: myStudentOrganizer.viewAllAssignmentsByCourseCode()) {
            studentOrganizerModel.addElement(a);
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

    private class MarkCompleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = studentOrganizerList.getSelectedIndex();
            studentOrganizerModel
        }
    }

    private class ViewIncompleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
