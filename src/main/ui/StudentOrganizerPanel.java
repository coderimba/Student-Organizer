package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

// based on code written in components-ListDemoProject's components.ListDemo
// as found in https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// Represents the Student Organizer's panel
public class StudentOrganizerPanel extends JPanel implements ListSelectionListener {
    private JList studentOrganizer;
    private DefaultListModel studentOrganizerModel;

    private static final String markCompleteString = "Mark Complete";
    private static final String viewIncompleteString = "View Incomplete";

    public StudentOrganizerPanel() {
        super(new BorderLayout());

        studentOrganizerModel = new DefaultListModel();

        studentOrganizer = new JList(studentOrganizerModel);
        studentOrganizer.setSelectionModel(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        studentOrganizer.setSelectedIndex(0);
        studentOrganizer.addListSelectionListener(this);
        studentOrganizer.setVisibleRowCount(5);
        JScrollPane studentOrganizerScrollPane = new JScrollPane(studentOrganizer);


    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
