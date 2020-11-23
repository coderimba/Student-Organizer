package model;

import model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {
    private Assignment testAssignment;

    @BeforeEach
    public void runBefore() {
        try {
            testAssignment = new Assignment(" Lab 4 ", " Cpsc 210 ", " 10-06 ", 2);
        } catch (AssignmentException e) {
            fail("Unexpected AssignmentException thrown");
        }
    }

    @Test
    public void testFourArgumentConstructor() {
        assertEquals("Lab 4", testAssignment.getName());
        assertEquals("CPSC 210" ,testAssignment.getCourseCode());
        assertEquals("10-06", testAssignment.getDueDate());
        assertEquals(2, testAssignment.getEstimatedHours());
        assertFalse(testAssignment.isComplete());
    }

    @Test
    public void testFiveArgumentConstructor() {
        try {
            testAssignment = new Assignment("Lab 4", "CPSC 210", "10-06", 2, false);
        } catch (AssignmentException e) {
            fail("Unexpected AssignmentException thrown");
        }
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

    @Test
    public void testToString() {
        assertEquals("[ CPSC 210 Lab 4 (due: 10-06, estimated time: 2.0 hours, Incomplete) ]", testAssignment.toString());
        try {
            testAssignment = new Assignment("Project 1", "cPSc 101", "09-08", 12.5);
        } catch (AssignmentException e) {
            fail("Unexpected AssignmentException thrown");
        }
        testAssignment.markComplete();
        assertEquals("[ CPSC 101 Project 1 (due: 09-08, estimated time: 12.5 hours, Completed) ]", testAssignment.toString());
    }

    @Test
    public void testSetNameThrowNameException() {
        try {
            testAssignment = new Assignment(" ", " ", " ", 0);
            fail("NameException expected");
        } catch (NameException e) {
            // pass
        } catch (AssignmentException e) {
            fail("NameException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetCourseCodeInvalidLength() {
        try {
            testAssignment = new Assignment("-test", " ", " ", 0);
            fail("CourseCodeException expected");
        } catch (NameException e) {
            fail("CourseCodeException expected");
        } catch (CourseCodeException e) {
            // pass
        } catch (AssignmentException e) {
            fail("CourseCodeException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetCourseCodeDoesNotBeginWithFourLetters() {
        try {
            testAssignment = new Assignment("-test", "121 CPSC", " ", 0);
            fail("CourseCodeException expected");
        } catch (NameException e) {
            fail("CourseCodeException expected");
        } catch (CourseCodeException e) {
            // pass
        } catch (AssignmentException e) {
            fail("CourseCodeException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetCourseCodeNoWhitespaceBetweenCourseAndNumber() {
        try {
            testAssignment = new Assignment("-test", "CPSC-121", " ", 0);
            fail("CourseCodeException expected");
        } catch (NameException e) {
            fail("CourseCodeException expected");
        } catch (CourseCodeException e) {
            // pass
        } catch (AssignmentException e) {
            fail("CourseCodeException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetCourseCodeDoesNotEndWithThreeDigits() {
        try {
            testAssignment = new Assignment("-test", "CPSC 12L", " ", 0);
            fail("CourseCodeException expected");
        } catch (NameException e) {
            fail("CourseCodeException expected");
        } catch (CourseCodeException e) {
            // pass
        } catch (AssignmentException e) {
            fail("CourseCodeException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateInvalidLength() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "11-1", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateNoHyphenBetweenMonthAndDay() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "11_11", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateMonthIsNotDigits() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "aa-11", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateDayIsNotDigits() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "11-bb", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateMonthIsLessThanOne() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "00-11", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateMonthIsMoreThanTwelve() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "13-11", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateDayIsLessThanOne() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "12-00", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateDayIsMoreThan31() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "12-32", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateInvalidDayForFebruary() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "02-30", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateValidDayForFebruary() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "02-28", 0.5);
        } catch (NameException e) {
            fail("No exception expected");
        } catch (CourseCodeException e) {
            fail("No exception expected");
        } catch (DueDateException e) {
            fail("No exception expected");
        } catch (EstimatedHoursException e) {
            fail("No exception expected");
        }
        assertEquals("-test", testAssignment.getName());
        assertEquals("CPSC 121" ,testAssignment.getCourseCode());
        assertEquals("02-28", testAssignment.getDueDate());
        assertEquals(0.5, testAssignment.getEstimatedHours());
        assertFalse(testAssignment.isComplete());
    }

    @Test
    public void testSetDueDateInvalidDayForApril() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "04-31", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateInvalidDayForJune() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "06-31", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateInvalidDayForSeptember() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "09-31", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetDueDateInvalidDayForNovember() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "11-31", 0);
            fail("DueDateException expected");
        } catch (NameException e) {
            fail("DueDateException expected");
        } catch (CourseCodeException e) {
            fail("DueDateException expected");
        } catch (DueDateException e) {
            // pass
        } catch (EstimatedHoursException e) {
            fail("DueDateException expected");
        }
        testFourArgumentConstructor();
    }

    @Test
    public void testSetEstimatedHoursThrowEstimatedHoursException() {
        try {
            testAssignment = new Assignment("-test", "CPSC 121", "11-30", 0);
            fail("EstimatedHoursException expected");
        } catch (NameException e) {
            fail("EstimatedHoursException expected");
        } catch (CourseCodeException e) {
            fail("EstimatedHoursException expected");
        } catch (DueDateException e) {
            fail("EstimatedHoursException expected");
        } catch (EstimatedHoursException e) {
            // pass
        }
        testFourArgumentConstructor();
    }
}
