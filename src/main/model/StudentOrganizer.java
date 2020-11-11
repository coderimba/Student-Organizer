package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// A representation of a list of assignments assigned to a university student.
public class StudentOrganizer implements Writable {
    private ArrayList<Assignment> studentOrganizer;

    // EFFECTS: constructs an empty StudentOrganizer
    public StudentOrganizer() {
        studentOrganizer = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds an assignment to the StudentOrganizer
    public void addAssignment(Assignment assignment) {
        studentOrganizer.add(assignment);
    }

    // REQUIRES: the StudentOrganizer contains an assignment with both the same name and courseCode as the arguments
    // MODIFIES: this
    // EFFECTS: deletes the assignment in the StudentOrganizer with both the same name and course code as the arguments
    public void deleteAssignment(String name, String courseCode) {
        boolean removedAssignment = false;
        int sizeBeforeRemoving = studentOrganizer.size();
        for (int i = 0; i < sizeBeforeRemoving && !removedAssignment; i++) {
            if (studentOrganizer.get(i).getName().equalsIgnoreCase(name.trim())
                    && studentOrganizer.get(i).getCourseCode().equalsIgnoreCase(courseCode.trim())) {
                studentOrganizer.remove(i);
                removedAssignment = true;
            }
        }
    }

    // REQUIRES: the StudentOrganizer contains an assignment with both the same name and courseCode as the arguments
    // MODIFIES: the Assignment in StudentOrganizer with both the same name and courseCode as the arguments
    // EFFECTS: marks the assignment in the StudentOrganizer with both the same name and course code as the arguments
    // as complete
    public void markAssignmentComplete(String name, String courseCode) {
        boolean markedComplete = false;
        for (int i = 0; i < studentOrganizer.size() && !markedComplete; i++) {
            if (studentOrganizer.get(i).getName().equalsIgnoreCase(name.trim())
                    && studentOrganizer.get(i).getCourseCode().equalsIgnoreCase(courseCode.trim())) {
                studentOrganizer.get(i).markComplete();
                markedComplete = true;
            }
        }
    }

    // REQUIRES: the StudentOrganizer contains at least one assignment
    // EFFECTS: returns all the assignments sorted by course code
    public ArrayList<Assignment> viewAllAssignmentsByCourseCode() {
        ArrayList<Assignment> allByCourseCode = insertionSortByCourseCode(studentOrganizer);
        return allByCourseCode;
    }

    // REQUIRES: the StudentOrganizer contains at least one incomplete assignment
    // EFFECTS: returns the incomplete assignments sorted by course code
    public ArrayList<Assignment> viewIncompleteAssignmentsByCourseCode() {
        ArrayList<Assignment> incompleteByCourseCode = insertionSortByCourseCode(incomplete());
        return incompleteByCourseCode;
    }

    // REQUIRES: the StudentOrganizer contains at least one incomplete assignment
    // EFFECTS: returns the incomplete assignments sorted by the due date
    public ArrayList<Assignment> viewIncompleteAssignmentsByDueDate() { // adapted insertion sort algorithm from
                                                                        // https://www.geeksforgeeks.org/insertion-sort/
        ArrayList<Assignment> incompleteByDueDate = incomplete();
        for (int i = 1; i < incompleteByDueDate.size(); i++) {
            Assignment temp = incompleteByDueDate.get(i);
            int pos = i - 1;
            while (pos >= 0 && incompleteByDueDate.get(pos).getDueDate().compareTo(temp.getDueDate()) > 0) {
                incompleteByDueDate.set(pos + 1, incompleteByDueDate.get(pos));
                pos--;
            }
            incompleteByDueDate.set(pos + 1, temp);
        }
        return incompleteByDueDate;
    }

    // REQUIRES: the StudentOrganizer contains at least one incomplete assignment
    // EFFECTS: returns the incomplete assignments sorted by estimated time for completion
    public ArrayList<Assignment> viewIncompleteAssignmentsByEstimatedHours() { // adapted insertion sort algorithm from
                                                                        // https://www.geeksforgeeks.org/insertion-sort/
        ArrayList<Assignment> incompleteByEstimatedHours = incomplete();
        for (int i = 1; i < incompleteByEstimatedHours.size(); i++) {
            Assignment temp = incompleteByEstimatedHours.get(i);
            int pos = i - 1;
            while (pos >= 0 && incompleteByEstimatedHours.get(pos).getEstimatedHours() > temp.getEstimatedHours()) {
                incompleteByEstimatedHours.set(pos + 1, incompleteByEstimatedHours.get(pos));
                pos--;
            }
            incompleteByEstimatedHours.set(pos + 1, temp);
        }
        return incompleteByEstimatedHours;
    }

    // REQUIRES: the StudentOrganizer contains at least one incomplete assignment
    // EFFECTS: returns the incomplete assignments
    private ArrayList<Assignment> incomplete() {
        ArrayList<Assignment> incompleteAssignments = new ArrayList<>();
        for (Assignment a: studentOrganizer) {
            if (!a.isComplete()) {
                incompleteAssignments.add(a);
            }
        }
        return incompleteAssignments;
    }

    // REQUIRES: list.size() >= 1
    // MODIFIES: ArrayList<Assignment> list (the argument)
    // EFFECTS: sorts list based on course code
    private ArrayList<Assignment> insertionSortByCourseCode(ArrayList<Assignment> list) {
        // adapted insertion sort algorithm from https://www.geeksforgeeks.org/insertion-sort/
        for (int i = 1; i < list.size(); i++) {
            Assignment temp = list.get(i);
            int pos = i - 1;
            while (pos >= 0
                    && !list.get(pos).getCourseCode().equals(temp.getCourseCode())) {
                    // check that course code of assignment in list at current position does not equal to
                    // course code of temp assignment
                list.set(pos + 1, list.get(pos));
                pos--;
            }
            list.set(pos + 1, temp);
        }
        return list;
    }

    // EFFECTS: returns the number of assignments in the StudentOrganizer
    public int size() {
        return studentOrganizer.size();
    }

    @Override
    public JSONObject toJson() { // based on code written in JsonSerializationDemo's model.WorkRoom class'
                        // toJson() method https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        JSONObject json = new JSONObject();
        json.put("assignments", assignmentsToJson());
        return json;
    }

    // EFFECTS: returns assignments in this student organizer as a JSON array
    private JSONArray assignmentsToJson() { // based on code written in JsonSerializationDemo's model.WorkRoom class'
                            // thingiesToJson() method https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        JSONArray jsonArray = new JSONArray();

        for (Assignment a: studentOrganizer) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }
}
