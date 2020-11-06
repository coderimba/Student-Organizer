package ui;

import javax.swing.*;
import java.awt.event.*;

// based on code written in components-MenuDemoProject's components.MenuDemo
// as found in https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// Represents the Student Organizer's menu
public class StudentOrganizerMenu implements ActionListener, ItemListener {
    private StudentOrganizerPanel panel;

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_M);
        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program"); // remove if unnecessary
        menuBar.add(menu);

        menuItem = new JMenuItem("Load Data", KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Save Data", KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
