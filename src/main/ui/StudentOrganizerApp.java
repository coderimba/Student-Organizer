package ui;

import model.Assignment;
import model.StudentOrganizer;
import model.exceptions.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// based on code written in Teller's ui.TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp
// and JsonSerializationDemo's ui.WorkRoomApp https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents the Student Organizer Application
public class StudentOrganizerApp {
    private static final String JSON_STORE = "./data/studentorganizer.json";
    private StudentOrganizer myStudentOrganizer;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String name;
    private String courseCode;
    private ArrayList<Assignment> sortedAssignments;

    // EFFECTS: runs the Student Organizer Application
    public StudentOrganizerApp() throws FileNotFoundException { // based on code written in JsonSerializationDemo's
                // ui.WorkRoomApp WorkRoomApp() method https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        init();
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user's input(s) from the main menu
    private void runApp() { // based on code written in Teller's ui.TellerApp runTeller() method
                            // https://github.students.cs.ubc.ca/CPSC210/TellerApp
        boolean exit = false;
        String command;

        while (!exit) {
            displayMainMenu();
            command = input.nextLine().toLowerCase().trim();

            if (command.equals("e")) {
                exit = true;
            } else {
                processCommandMainMenu(command);
            }
        }

        System.out.println("\nApplication terminated. Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes StudentOrganizer, Scanner, JsonWriter, and JsonReader objects
    private void init() { // based on code written in Teller's ui.TellerApp init() method
        // https://github.students.cs.ubc.ca/CPSC210/TellerApp and JsonSerializationDemo's ui.WorkRoomApp
        // WorkRoomApp() constructor https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        myStudentOrganizer = new StudentOrganizer();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays items from the main menu to the user
    private void displayMainMenu() { // based on code written in JsonSerializationDemo's ui.WorkRoomApp
                // displayMenu() method https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        System.out.println("\nChoose an item from the menu:");
        System.out.println("\t(a) Add Assignment");
        System.out.println("\t(d) Delete Assignment");
        System.out.println("\t(m) Mark Assignment Complete");
        System.out.println("\t(v) View Assignments");
        System.out.println("\t(s) Save Student Organizer to file");
        System.out.println("\t(l) Load Student Organizer from file");
        System.out.println("\t(e) Exit");
    }

    // MODIFIES: this
    // EFFECTS: processes user's command from the main menu
    private void processCommandMainMenu(String command) { // based on code written in Teller's ui.TellerApp
                                // processCommand method https://github.students.cs.ubc.ca/CPSC210/TellerApp
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
            case "s":
                saveStudentOrganizer();
                break;
            case "l":
                loadStudentOrganizer();
                break;
            default:
                System.out.println("Invalid selection. Try again.");
                break;
        }
    }

    // REQUIRES: name contains at least one letter or digit. courseCode has a length of 8, following the format of
    // 4 letters, 1 whitespace, and 3 digits, excluding any whitespace before or after the first and last characters,
    // respectively (e.g. " Cpsc 210 " is acceptable). dueDate is in the format mm-dd and must be valid e.g. mm cannot
    // be 13, and dd cannot be 30 if mm is 02 (there is no such thing as 30 February). estimatedHours > 0 and has to
    // be digit(s)
    // MODIFIES: this
    // EFFECTS: prompts user to add an assignment to the StudentOrganizer
    private void addMyAssignment() {
        String dueDate;
        double estimatedHours;

        enterNameAndCourseCode();
        System.out.print("Enter the due date of the assignment in the format mm-dd (e.g. 09-08 for September 8): ");
        dueDate = input.nextLine();
        System.out.print("Enter the estimated number of hours needed to complete assignment (e.g. 0.5 for 30 min): ");
        try {
            estimatedHours = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            estimatedHours = 0;
        }

        try {
            myStudentOrganizer.addAssignment(new Assignment(name, courseCode, dueDate, estimatedHours));
        } catch (NameException e) {
            System.out.println("Invalid name. Couldn't add assignment.");
        } catch (CourseCodeException e) {
            System.out.println("Invalid course code. Couldn't add assignment.");
        } catch (DueDateException e) {
            System.out.println("Invalid due date. Couldn't add assignment.");
        } catch (EstimatedHoursException e) {
            System.out.println("Invalid estimated number of hours. Couldn't add assignment.");
        }
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
    private void viewMyAssignments() { // based on code written in Teller's ui.TellerApp runTeller() method
                                       // https://github.students.cs.ubc.ca/CPSC210/TellerApp
        boolean end = false;
        String command;

        while (!end) {
            displaySortAssignmentsMenu();
            command = input.nextLine().trim();

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
        System.out.print("Enter the name of the assignment: ");
        name = input.nextLine();
        System.out.print("Enter the course code of the assignment (e.g. CPSC 210): ");
        courseCode = input.nextLine();
    }

    // EFFECTS: displays different ways assignments can be sorted from the menu to the user
    private void displaySortAssignmentsMenu() { // based on code written in Teller's ui.TellerApp displayMenu() method
                                                // https://github.students.cs.ubc.ca/CPSC210/TellerApp
        System.out.println("\nHow would you like your assignments to be sorted?");
        System.out.println("\t(1) By course code, all assignments");
        System.out.println("\t(2) By course code, incomplete assignments");
        System.out.println("\t(3) By due date, incomplete assignments");
        System.out.println("\t(4) By estimated time for completion, incomplete assignments");
        System.out.println("\t(5) Back to main menu");
    }

    // MODIFIES: this
    // EFFECTS: processes user's command from the Sort Assignments menu
    private void processCommandSortAssignmentsMenu(String command) { // based on code written in Teller's ui.TellerApp
                                            // processCommand method https://github.students.cs.ubc.ca/CPSC210/TellerApp
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
    private void printAssignments() { // based on code written in Teller's ui.TellerApp printBalance method
                                      // https://github.students.cs.ubc.ca/CPSC210/TellerApp
        for (Assignment a: sortedAssignments) {
            System.out.println(a);
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

    // MODIFIES: this
    // EFFECTS: loads Student Organizer from file
    private void loadStudentOrganizer() { // based on code written in JsonSerializationDemo's ui.WorkRoomApp
                    // loadWorkRoom() method https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        try {
            myStudentOrganizer = jsonReader.read();
            System.out.printf("Loaded Student Organizer from %s\n", JSON_STORE);
        } catch (IOException e) {
            System.out.printf("Unable to read from file: %s\n", JSON_STORE);
        } catch (NameException e) {
            System.out.println("Invalid assignment in file; NameException thrown");
        } catch (CourseCodeException e) {
            System.out.println("Invalid assignment in file; CourseCodeException thrown");
        } catch (DueDateException e) {
            System.out.println("Invalid assignment in file; DueDateException thrown");
        } catch (EstimatedHoursException e) {
            System.out.println("Invalid assignment in file; EstimatedHoursException thrown");
        }
    }
}
