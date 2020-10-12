package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StudentOrganizerTest {
    private StudentOrganizer testStudentOrganizer;

    @BeforeEach
    public void runBefore() {
        testStudentOrganizer = new StudentOrganizer();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testStudentOrganizer.size());
    }

    @Test
    public void testAddAssignment() {
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);
        assertEquals(1, testStudentOrganizer.size());

        Assignment a2 = new Assignment(" Lab 5 ", " Cpsc 210 ", " 10-20 ", 2);
        testStudentOrganizer.addAssignment(a2);
        assertEquals(2, testStudentOrganizer.size());
    }

    @Test
    public void testDeleteAssignment() {
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);
        Assignment a2 = new Assignment(" Lab 5 ", " Cpsc 210 ", " 10-20 ", 2);
        testStudentOrganizer.addAssignment(a2);
        Assignment a3 = new Assignment("lab 4 ", " cpsc 121", " 10-01",2);
        testStudentOrganizer.addAssignment(a3);

        testStudentOrganizer.deleteAssignment("  LAB 5  ", "cPSC 210");
        assertEquals(2, testStudentOrganizer.size());
        testStudentOrganizer.deleteAssignment("Lab 4", " Cpsc 121 ");
        assertEquals(1, testStudentOrganizer.size());
        testStudentOrganizer.deleteAssignment("lab 4", " cpsc 210 ");
        assertEquals(0, testStudentOrganizer.size());
    }

    @Test
    public void testMarkAssignmentComplete() {
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06".toString(), 2);
        testStudentOrganizer.addAssignment(a1);
        Assignment a2 = new Assignment(" Lab 5 ", " Cpsc 210 ", " 10-20 ", 2);
        testStudentOrganizer.addAssignment(a2);
        Assignment a3 = new Assignment("lab 4 ", " cpsc 121"," 10-01",2);
        testStudentOrganizer.addAssignment(a3);

        testStudentOrganizer.markAssignmentComplete("  LAB 5  ", "cPSC 210");
        assertTrue(a2.isComplete());
        testStudentOrganizer.markAssignmentComplete("Lab 4", " Cpsc 121 ");
        assertTrue(a3.isComplete());
        testStudentOrganizer.markAssignmentComplete("lab 4", " cpsc 210 ");
        assertTrue(a1.isComplete());
    }

    @Test
    public void testViewAllAssignmentsByCourseCodeOneAssignment() {
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);

        ArrayList<Assignment> allByCourseCode = testStudentOrganizer.viewAllAssignmentsByCourseCode();
        assertEquals(1,allByCourseCode.size());
        assertEquals("CPSC 210", allByCourseCode.get(0).getCourseCode());
    }

    @Test
    public void testViewAllAssignmentsByCourseCodeManyAssignmentsSameCourseCode() {
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);
        Assignment a2 = new Assignment(" Lab 5 ", " Cpsc 210 ", " 10-20 ", 2);
        testStudentOrganizer.addAssignment(a2);
        Assignment a3 = new Assignment("P1", "CPSC 210", " 10-14",10);
        testStudentOrganizer.addAssignment(a3);

        ArrayList<Assignment> allByCourseCode = testStudentOrganizer.viewAllAssignmentsByCourseCode();
        assertEquals(3,allByCourseCode.size());
        for (Assignment a: allByCourseCode)
            assertEquals("CPSC 210", a.getCourseCode());
    }

    @Test
    public void testViewAllAssignmentsByCourseCodeManyAssignmentsDifferentCourseCodes() {
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);
        Assignment a2 = new Assignment("a2", "  cpsc 121"," 10-14 ",4);
        testStudentOrganizer.addAssignment(a2);
        Assignment a3 = new Assignment("P1", "CPSC 210", " 10-14 ",10);
        testStudentOrganizer.addAssignment(a3);
        Assignment a4 = new Assignment("Quiz 6","econ 101", " 10-25",0.5);
        testStudentOrganizer.addAssignment(a4);
        Assignment a5 = new Assignment("A5", "math 200  ", "10-18 ", 3);
        testStudentOrganizer.addAssignment(a5);
        Assignment a6 = new Assignment("Pre-lab 5", " CPSC 121 ", " 10-15 ", 1);
        testStudentOrganizer.addAssignment(a6);
        Assignment a7 = new Assignment("C1 Lecture Ticket", "cpsc 210", " 10-13 ",0.25);
        testStudentOrganizer.addAssignment(a7);

        ArrayList<Assignment> allByCourseCode = testStudentOrganizer.viewAllAssignmentsByCourseCode();
        assertEquals(7,allByCourseCode.size());
        assertEquals("MATH 200",allByCourseCode.get(0).getCourseCode());
        assertEquals("ECON 101",allByCourseCode.get(1).getCourseCode());
        assertEquals("CPSC 121",allByCourseCode.get(2).getCourseCode());
        assertEquals("CPSC 121",allByCourseCode.get(3).getCourseCode());
        assertEquals("CPSC 210",allByCourseCode.get(4).getCourseCode());
        assertEquals("CPSC 210",allByCourseCode.get(5).getCourseCode());
        assertEquals("CPSC 210",allByCourseCode.get(6).getCourseCode());
    }

    @Test
    public void testViewIncompleteAssignmentsByCourseCodeOneAssignment() {
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);
        Assignment a2 = new Assignment(" Lab 5 ", " Cpsc 210 ", " 10-20 ", 2);
        testStudentOrganizer.addAssignment(a2);

        testStudentOrganizer.markAssignmentComplete("  LAB 5  ", "cPSC 210");

        ArrayList<Assignment> incompleteByCourseCode = testStudentOrganizer.viewIncompleteAssignmentsByCourseCode();
        assertEquals(1,incompleteByCourseCode.size());
        assertEquals("CPSC 210", incompleteByCourseCode.get(0).getCourseCode());
    }

    @Test
    public void testViewIncompleteAssignmentsByCourseCodeManyAssignmentsSameCourseCode() {
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);
        Assignment a2 = new Assignment(" Lab 5 ", " Cpsc 210 ", " 10-20 ", 2);
        testStudentOrganizer.addAssignment(a2);
        Assignment a3 = new Assignment("P1", "CPSC 210", " 10-14",10);
        testStudentOrganizer.addAssignment(a3);

        testStudentOrganizer.markAssignmentComplete("  LAB 5  ", "cPSC 210");

        ArrayList<Assignment> incompleteByCourseCode = testStudentOrganizer.viewIncompleteAssignmentsByCourseCode();
        assertEquals(2,incompleteByCourseCode.size());
        for (Assignment a: incompleteByCourseCode)
            assertEquals("CPSC 210", a.getCourseCode());
    }

    @Test
    public void testViewIncompleteAssignmentsByCourseCodeManyAssignmentsDifferentCourseCode() {
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);
        Assignment a2 = new Assignment("a2", "  cpsc 121"," 10-14 ",4);
        testStudentOrganizer.addAssignment(a2);
        Assignment a3 = new Assignment("P1", "CPSC 210", " 10-14 ",10);
        testStudentOrganizer.addAssignment(a3);
        Assignment a4 = new Assignment("Quiz 6","econ 101", " 10-25",0.5);
        testStudentOrganizer.addAssignment(a4);
        Assignment a5 = new Assignment("A5", "math 200  ", "10-18 ", 3);
        testStudentOrganizer.addAssignment(a5);
        Assignment a6 = new Assignment("Pre-lab 5", " CPSC 121 ", " 10-15 ", 1);
        testStudentOrganizer.addAssignment(a6);
        Assignment a7 = new Assignment("C1 Lecture Ticket", "cpsc 210", " 10-13 ",0.25);
        testStudentOrganizer.addAssignment(a7);

        testStudentOrganizer.markAssignmentComplete("  p1", "cPSC 210");
        testStudentOrganizer.markAssignmentComplete("a5 ", "Math 200");

        ArrayList<Assignment> incompleteByCourseCode = testStudentOrganizer.viewIncompleteAssignmentsByCourseCode();
        assertEquals(5,incompleteByCourseCode.size());
        assertEquals("ECON 101",incompleteByCourseCode.get(0).getCourseCode());
        assertEquals("CPSC 121",incompleteByCourseCode.get(1).getCourseCode());
        assertEquals("CPSC 121",incompleteByCourseCode.get(2).getCourseCode());
        assertEquals("CPSC 210",incompleteByCourseCode.get(3).getCourseCode());
        assertEquals("CPSC 210",incompleteByCourseCode.get(4).getCourseCode());
    }

    @Test
    public void testViewIncompleteAssignmentsByDueDateOneAssignment() {
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);
        Assignment a2 = new Assignment(" Lab 5 ", " Cpsc 210 ", " 10-20 ", 2);
        testStudentOrganizer.addAssignment(a2);

        testStudentOrganizer.markAssignmentComplete("  LAB 5  ", "cPSC 210");

        ArrayList<Assignment> incompleteByDueDate = testStudentOrganizer.viewIncompleteAssignmentsByDueDate();
        assertEquals(1,incompleteByDueDate.size());
        assertEquals("10-06", incompleteByDueDate.get(0).getDueDate());
    }

    @Test
    public void testViewIncompleteAssignmentsByDueDateManyAssignments() {
        Assignment a0 = new Assignment("hypothetical", "cpsc 210","12-04",5);
        testStudentOrganizer.addAssignment(a0);
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);
        Assignment a2 = new Assignment("a2", "  cpsc 121"," 10-14",4);
        testStudentOrganizer.addAssignment(a2);
        Assignment a3 = new Assignment("P1", "CPSC 210", " 10-14",10);
        testStudentOrganizer.addAssignment(a3);
        Assignment a4 = new Assignment("Quiz 7","econ 101", "11-01 ",0.5);
        testStudentOrganizer.addAssignment(a4);
        Assignment a5 = new Assignment("A5", "math 200  ", " 10-18", 3);
        testStudentOrganizer.addAssignment(a5);
        Assignment a6 = new Assignment("Pre-lab 5", " CPSC 121 ", " 10-15", 1);
        testStudentOrganizer.addAssignment(a6);

        testStudentOrganizer.markAssignmentComplete("a5 ", "Math 200");

        ArrayList<Assignment> incompleteByDueDate = testStudentOrganizer.viewIncompleteAssignmentsByDueDate();
        assertEquals(6,incompleteByDueDate.size());
        assertEquals("10-06", incompleteByDueDate.get(0).getDueDate());
        assertEquals("10-14", incompleteByDueDate.get(1).getDueDate());
        assertEquals("10-14", incompleteByDueDate.get(2).getDueDate());
        assertEquals("10-15", incompleteByDueDate.get(3).getDueDate());
        assertEquals("11-01", incompleteByDueDate.get(4).getDueDate());
        assertEquals("12-04", incompleteByDueDate.get(5).getDueDate());
    }

    @Test
    public void testViewIncompleteAssignmentsByEstimatedHoursOneAssignment() {
        Assignment a0 = new Assignment("hypothetical", "cpsc 210","12-04",5);
        testStudentOrganizer.addAssignment(a0);
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);

        testStudentOrganizer.markAssignmentComplete("  LAB 4  ", "cPSC 210");

        ArrayList<Assignment> incompleteByEstimatedHours = testStudentOrganizer.viewIncompleteAssignmentsByEstimatedHours();
        assertEquals(1,incompleteByEstimatedHours.size());
        assertEquals(5, incompleteByEstimatedHours.get(0).getEstimatedHours());
    }

    @Test
    public void testViewIncompleteAssignmentsByEstimatedHoursManyAssignments() {
        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);
        Assignment a2 = new Assignment("a2", "  cpsc 121"," 10-14",4);
        testStudentOrganizer.addAssignment(a2);
        Assignment a3 = new Assignment("P1", "CPSC 210", " 10-14",10);
        testStudentOrganizer.addAssignment(a3);
        Assignment a4 = new Assignment("Quiz 7","econ 101", "11-01 ",0.5);
        testStudentOrganizer.addAssignment(a4);
        Assignment a5 = new Assignment("A5", "math 200  ", " 10-18", 3);
        testStudentOrganizer.addAssignment(a5);
        Assignment a6 = new Assignment("Pre-lab 5", " CPSC 121 ", " 10-15", 1);
        testStudentOrganizer.addAssignment(a6);
        Assignment a7 = new Assignment("C1 Lecture Ticket", "cpsc 210", "10-13",0.25);
        testStudentOrganizer.addAssignment(a7);

        testStudentOrganizer.markAssignmentComplete("a5 ", "Math 200");

        ArrayList<Assignment> incompleteByEstimatedHours = testStudentOrganizer.viewIncompleteAssignmentsByEstimatedHours();
        assertEquals(6,incompleteByEstimatedHours.size());
        assertEquals(0.25, incompleteByEstimatedHours.get(0).getEstimatedHours());
        assertEquals(0.5, incompleteByEstimatedHours.get(1).getEstimatedHours());
        assertEquals(1, incompleteByEstimatedHours.get(2).getEstimatedHours());
        assertEquals(2, incompleteByEstimatedHours.get(3).getEstimatedHours());
        assertEquals(4, incompleteByEstimatedHours.get(4).getEstimatedHours());
        assertEquals(10, incompleteByEstimatedHours.get(5).getEstimatedHours());
    }

    @Test
    public void testSize() {
        assertEquals(0,testStudentOrganizer.size());

        Assignment a1 = new Assignment(" Lab 4 ", " Cpsc 210 ", "10-06", 2);
        testStudentOrganizer.addAssignment(a1);
        Assignment a2 = new Assignment(" Lab 5 ", " Cpsc 210 ", " 10-20 ", 2);
        testStudentOrganizer.addAssignment(a2);
        Assignment a3 = new Assignment("P1", "CPSC 210", " 10-14",10);
        testStudentOrganizer.addAssignment(a3);

        assertEquals(3,testStudentOrganizer.size());
    }
}