package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

// based on code written in components-ListDemoProject's components.ListDemo
// as found in https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// Represents the Student Organizer's GUI
public class GraphicalStudentOrganizer extends JPanel implements ListSelectionListener {
    private JList studentOrganizer;
    private DefaultListModel studentOrganizerModel;

    private static final String loadDataString = "Load Data";
    private static final String saveDataString = "Save Data";
    private JButton loadButton;
    private JButton saveButton;

    public GraphicalStudentOrganizer() {
        super(new BorderLayout());

        studentOrganizerModel = new DefaultListModel();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
