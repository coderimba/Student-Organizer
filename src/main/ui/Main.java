package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) { // based on code written in JsonSerializationDemo's ui.Main
        try {
            new StudentOrganizerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
