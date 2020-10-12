package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {
    private Assignment testAssignment;

    @BeforeEach
    public void runBefore() {
        testAssignment = new Assignment(" Lab 4 ", " Cpsc 210 ", " 10-06 ", 2);
    }

    @Test
    public void testConstructor() {
        assertEquals("Lab 4", testAssignment.getName());
        assertEquals("CPSC 210" ,testAssignment.getCourseCode());
        assertEquals("10-06", testAssignment.getDueDate());
        assertEquals(2, testAssignment.getEstimatedHours());
        assertFalse(testAssignment.isComplete());
    }

    @Test
    public void testMarkComplete() {
        testAssignment.markComplete();
        assertTrue(testAssignment.isComplete());
    }
}
