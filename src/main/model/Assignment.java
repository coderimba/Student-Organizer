package model;

// Represents an assignment with a name, a course code, a due date, an estimated time for completion (in hours),
// and whether or not it has been completed.
public class Assignment {
    private String name;
    private String courseCode;
    private String dueDate;
    private double estimatedHours;
    private boolean complete;

    // REQUIRES: name contains at least one letter or digit. courseCode has a length of 8, following the format of
    // 4 letters, 1 whitespace, and 3 digits, excluding any whitespace before or after the first and last characters,
    // respectively (e.g. " Cpsc 210 " is acceptable). dueDate is in the format mm-dd
    // EFFECTS: constructs an Assignment with a name, a corresponding course, a due date,
    // an estimated number of hours needed for completion, and it is marked as incomplete by default
    public Assignment(String name, String courseCode, String dueDate, double estimatedHours) {
        setName(name.trim());
        setCourseCode(courseCode.toUpperCase().trim());
        setDueDate(dueDate.trim());
        setEstimatedHours(estimatedHours);
        setComplete(false);
    }

    // REQUIRES: complete = false
    // MODIFIES: this
    // EFFECTS: marks assignment as complete
    public void markComplete() {
        setComplete(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(double estimatedHours) {
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
}
