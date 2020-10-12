package ui;

import model.StudentOrganizer;

import java.util.Scanner;

// Student Organizer Application
public class StudentOrganizerApp {
    private StudentOrganizer myStudentOrganizer;
    private Scanner input;

    // EFFECTS: runs the Student Organizer Application
    public StudentOrganizerApp() {
        runApp();
    }

    // REQUIRES: user is to key in an integer when prompted
    // MODIFIES: this
    // EFFECTS: processes user's input(s)
    private void runApp() {
        boolean exit = false;
        int command;

        init();

        while (!exit) {
            displayMenu();
            command = input.nextInt();

            if (command == 5) {
                exit = true;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nApplication terminated. Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes StudentOrganizer and Scanner objects
    private void init() {
        myStudentOrganizer = new StudentOrganizer();
        input = new Scanner(System.in);
    }

    // EFFECTS: displays items from the menu to the user
    private void displayMenu() {
        System.out.println("Choose an item from the menu:");
        System.out.println("\t(1) Add Assignment");
        System.out.println("\t(2) Delete Assignment");
        System.out.println("\t(3) Mark Assignment Complete");
        System.out.println("\t(4) Sort Assignments");
        System.out.println("\t(5) Exit");
    }

    // MODIFIES: this
    // EFFECTS: processes user's command
    private void processCommand(int command) {
        switch (command) {
            case 1:
                //add assignment
                break;
            case 2:
                //delete assignment
                break;
            case 3:
                //mark assignment complete
                break;
            case 4:
                //sort assignments
                break;
            default:
                System.out.println("Invalid selection. Try again.");
                break;
        }
    }
}
