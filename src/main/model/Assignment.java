package model;

import model.exceptions.CourseCodeException;
import model.exceptions.DueDateException;
import model.exceptions.EstimatedHoursException;
import model.exceptions.NameException;
import org.json.JSONObject;
import persistence.Writable;

// Represents an assignment with a name, a course code, a due date, an estimated time for completion (in hours),
// and whether or not it has been completed.
public class Assignment implements Writable {
    private String name;
    private String courseCode;
    private String dueDate;
    private double estimatedHours;
    private boolean complete;

    // EFFECTS: constructs an Assignment with a name, a corresponding course, a due date,
    // an estimated number of hours needed for completion, and it is marked as incomplete by default.
    public Assignment(String name, String courseCode, String dueDate, double estimatedHours)
            throws NameException, CourseCodeException, DueDateException, EstimatedHoursException,
            NumberFormatException {
        setName(name);
        setCourseCode(courseCode);
        setDueDate(dueDate);
        setEstimatedHours(estimatedHours);
        setComplete(false);
    }

    // EFFECTS: constructs an Assignment with a name, a corresponding course, a due date,
    // an estimated number of hours needed for completion, and its completion status.
    public Assignment(String name, String courseCode, String dueDate, double estimatedHours, boolean complete)
            throws NameException, CourseCodeException, DueDateException, EstimatedHoursException,
            NumberFormatException {
        setName(name);
        setCourseCode(courseCode);
        setDueDate(dueDate);
        setEstimatedHours(estimatedHours);
        setComplete(complete);
    }

    // MODIFIES: this
    // EFFECTS: marks assignment as complete
    public void markComplete() {
        setComplete(true);
    }

    public String getName() {
        return name;
    }

    // EFFECTS: name must contain at least one letter or digit, otherwise NameException is thrown.
    public void setName(String name) throws NameException {
        name.trim();
        boolean validName = false;
        for (int i = 0; i < name.length(); i++) {
            if (Character.isLetterOrDigit(name.charAt(i))) {
                validName = true;
            }
            if (validName) {
                break;
            }
        }
        if (validName) {
            this.name = name;
        } else {
            throw new NameException();
        }
    }

    public String getCourseCode() {
        return courseCode;
    }

    // EFFECTS: courseCode must have a length of 8, following the format of 4 letters, 1 whitespace, and 3 digits,
    // excluding any whitespace before or after the first and last characters, respectively (e.g. " Cpsc 210 " is
    // acceptable), otherwise CourseCodeException is thrown.
    public void setCourseCode(String courseCode) throws CourseCodeException {
        courseCode.toUpperCase().trim();
        boolean validCourseCode = true;
        if (courseCode.length() != 8) {
            throw new CourseCodeException();
        }
        for (int i = 0; i < 4; i++) {
            if (!Character.isLetter(courseCode.charAt(i))) {
                validCourseCode = false;
            }
            if (!validCourseCode) {
                throw new CourseCodeException();
            }
        }
        if (courseCode.charAt(4) != 32) { // checking if the char at index 4 is a whitespace; referenced ASCII Table
                                            // from here: http://www.asciitable.com/
            throw new CourseCodeException();
        }
        for (int i = 5; i < 8; i++) {
            if (!Character.isDigit(courseCode.charAt(i))) {
                validCourseCode = false;
            }
            if (!validCourseCode) {
                throw new CourseCodeException();
            }
        }
        this.courseCode = courseCode;
    }

    public String getDueDate() {
        return dueDate;
    }

    // EFFECTS: dueDate must be in the format mm-dd and has to be valid (e.g. 13-10 or 02-30 are invalid) otherwise
    // DueDateException is thrown (whitespace is allowed before and after the first and last characters of the string).
    public void setDueDate(String dueDate) throws DueDateException {
        dueDate.trim();
        int month;
        int day;
        if (dueDate.length() != 5 || dueDate.charAt(2) != 45) { // checking length of dueDate and if the char at index 2
                                                                // is a hyphen; referenced ASCII Table from here:
                                                                // http://www.asciitable.com/
            throw new DueDateException();
        }
        try {
            month = Integer.parseInt(dueDate.substring(0,2));
            day = Integer.parseInt(dueDate.substring(3));
        } catch (NumberFormatException e) {
            throw new DueDateException();
        }
        if (month < 1 || month > 12 || day < 1 || day > 31) {
            throw new DueDateException();
        }
        if (month == 2 && day > 29) { // checking if February is valid
            throw new DueDateException();
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) { // checking if months with 30 days
                                                                                    // are valid
            throw new DueDateException();
        }
        this.dueDate = dueDate;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    // EFFECTS: estimatedHours must be greater than zero, otherwise EstimatedHoursException is thrown. If estimatedHours
    // is not digit(s), throw NumberFormatException
    public void setEstimatedHours(double estimatedHours) throws EstimatedHoursException, NumberFormatException {
        if (estimatedHours <= 0) {
            throw new EstimatedHoursException();
        }
        this.estimatedHours = estimatedHours;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    // EFFECTS: returns a string representation of assignment
    public String toString() {
        return "[ " + getCourseCode() + " " + getName() + " (due: " + getDueDate() + ", estimated time: "
                + getEstimatedHours() + " hours, " + (isComplete() ? "Completed" : "Incomplete") + ") ]";
    }

    @Override
    public JSONObject toJson() { // based on code written in JsonSerializationDemo's model.Thingy class' toJson() method
                                 // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("courseCode", getCourseCode());
        json.put("dueDate", getDueDate());
        json.put("estimatedHours", getEstimatedHours());
        json.put("complete", isComplete());
        return json;
    }
}
