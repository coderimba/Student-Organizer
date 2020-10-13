package ui;

import model.Assignment;
import model.StudentOrganizer;

import java.util.ArrayList;
import java.util.Scanner;

// Student Organizer Application
public class StudentOrganizerApp {
    private StudentOrganizer myStudentOrganizer;
    private Scanner input;
    private String name;
    private String courseCode;
    private ArrayList<Assignment> sortedAssignments;

    // EFFECTS: runs the Student Organizer Application
    public StudentOrganizerApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user's input(s) from the main menu
    private void runApp() {
        boolean exit = false;
        String command;

        init();

        while (!exit) {
            displayMainMenu();
            command = input.next().toLowerCase();

            if (command.equals("e")) {
                exit = true;
            } else {
                processCommandMainMenu(command);
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

    // EFFECTS: displays items from the main menu to the user
    private void displayMainMenu() {
        System.out.println("\nChoose an item from the menu:");
        System.out.println("\t(a) Add Assignment");
        System.out.println("\t(d) Delete Assignment");
        System.out.println("\t(m) Mark Assignment Complete");
        System.out.println("\t(v) View Assignments");
        System.out.println("\t(e) Exit");
    }

    // MODIFIES: this
    // EFFECTS: processes user's command from the main menu
    private void processCommandMainMenu(String command) {
        switch (command) {
            case "a":
                addMyAssignment();
                break;
            case "d":
                deleteMyAssignment();
                break;
            case "m":
                markMyAssignmentComplete();
                break;
            case "v":
                viewMyAssignments();
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
        System.out.print("Enter the due date of the assignment in the format mm-dd (e.g. 10-14 for October 14): ");
        dueDate = input.next();
        System.out.print("Enter the estimated number of hours needed to complete assignment (e.g. 0.5 for 30 min): ");
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

    // MODIFIES: this
    // EFFECTS: processes user's input(s) from the Sort Assignments menu
    private void viewMyAssignments() {
        boolean end = false;
        String command;

        while (!end) {
            displaySortAssignmentsMenu();
            command = input.next();

            if (command.equals("5")) {
                end = true;
            } else {
                processCommandSortAssignmentsMenu(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter the name and course code of assignment
    private void enterNameAndCourseCode() {
        input.nextLine(); //to discard the remaining parts of the input
        System.out.print("Enter the name of the assignment: ");
        name = input.nextLine();
        input.nextLine(); //to discard the remaining parts of the input
        System.out.print("Enter the course code of the assignment (e.g. CPSC 210): ");
        courseCode = input.nextLine();
        input.nextLine(); //to discard the remaining parts of the input
    }

    // EFFECTS: displays different ways assignments can be sorted from the menu to the user
    private void displaySortAssignmentsMenu() {
        System.out.println("\nHow would you like your assignments to be sorted?");
        System.out.println("\t(1) By course code, all assignments");
        System.out.println("\t(2) By course code, incomplete assignments");
        System.out.println("\t(3) By due date, incomplete assignments");
        System.out.println("\t(4) By estimated time for completion, incomplete assignments");
        System.out.println("\t(5) Back to main menu");
    }

    // MODIFIES: this
    // EFFECTS: processes user's command from the Sort Assignments menu
    private void processCommandSortAssignmentsMenu(String command) {
        switch (command) {
            case "1": sortedAssignments = myStudentOrganizer.viewAllAssignmentsByCourseCode();
                System.out.println("List of all assignments sorted by course code:");
                printAssignments();
                break;
            case "2": sortedAssignments = myStudentOrganizer.viewIncompleteAssignmentsByCourseCode();
                System.out.println("List of incomplete assignments sorted by course code:");
                printAssignments();
                break;
            case "3": sortedAssignments = myStudentOrganizer.viewIncompleteAssignmentsByDueDate();
                System.out.println("List of incomplete assignments sorted by due date:");
                printAssignments();
                break;
            case "4": sortedAssignments = myStudentOrganizer.viewIncompleteAssignmentsByEstimatedHours();
                System.out.println("List of incomplete assignments sorted by estimated time for completion:");
                printAssignments();
                break;
            default:
                System.out.println("Invalid selection. Try again.");
                break;
        }
    }

    // EFFECTS: prints the list of assignments based on the type of sort selected
    private void printAssignments() {
        for (Assignment a: sortedAssignments) {
            System.out.println(a);
        }
    }
}
