package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) { // based on code written in JsonSerializationDemo's ui.Main
                                             // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        try {
            new StudentOrganizerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
