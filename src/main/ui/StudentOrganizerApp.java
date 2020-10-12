package ui;

import model.Assignment;
import model.StudentOrganizer;

import java.util.Scanner;

// Student Organizer Application
public class StudentOrganizerApp {
    private StudentOrganizer myStudentOrganizer;
    private Scanner input;
    private String name;
    private String courseCode;

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
                addMyAssignment();
                break;
            case 2:
                deleteMyAssignment();
                break;
            case 3:
                markMyAssignmentComplete();
                break;
            case 4:
                sortMyAssignments();
                break;
            default:
                System.out.println("Invalid selection. Try again.");
                break;
        }
    }

    // REQUIRES: name contains at least one letter or digit. courseCode has a length of 8, following the format of
    // 4 letters, 1 whitespace, and 3 digits, excluding any whitespace before or after the first and last characters,
    // respectively (e.g. " Cpsc 210 " is acceptable). dueDate is in the format mm-dd and must be valid e.g. mm cannot
    // be 13, and dd cannot be 30 if mm is 02 (there is no such thing as 30 February)
    // MODIFIES: this
    // EFFECTS: prompts user to add an assignment to the StudentOrganizer
    private void addMyAssignment() {
        String dueDate;
        double estimatedHours;

        enterNameAndCourseCode();
        System.out.println("Enter the due date of the assignment in the format mm-dd (e.g. 10-14 for October 14):");
        dueDate = input.next();
        System.out.println("Enter the estimated number of hours needed to complete assignment (e.g. 0.5 for 30 min):");
        estimatedHours = input.nextDouble();

        myStudentOrganizer.addAssignment(new Assignment(name, courseCode, dueDate, estimatedHours));
    }

    // REQUIRES: the StudentOrganizer contains an assignment with both the same name and courseCode as the user's inputs
    // MODIFIES: this
    // EFFECTS: prompts user to delete an assignment in the StudentOrganizer
    private void deleteMyAssignment() {
        enterNameAndCourseCode();

        myStudentOrganizer.deleteAssignment(name, courseCode);
    }

    // REQUIRES: the StudentOrganizer contains an assignment with both the same name and courseCode as the user's inputs
    // MODIFIES: this, the Assignment in StudentOrganizer with both the same name and courseCode as the user's inputs
    // EFFECTS: prompts user to mark an assignment in the StudentOrganizer as complete
    private void markMyAssignmentComplete() {
        enterNameAndCourseCode();

        myStudentOrganizer.markAssignmentComplete(name, courseCode);
    }

    // EFFECTS: prints the list of assignments based on the type of sort selected
    private void sortMyAssignments() {

    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter the name and course code of assignment
    private void enterNameAndCourseCode() {
        System.out.println("Enter the name of the assignment:");
        name = input.next();
        input.nextLine();
        System.out.println("Enter the course code of the assignment (e.g. CPSC 210):");
        courseCode = input.next();
        input.nextLine();
    }
}
