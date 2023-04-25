import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WFCTest {
	@Test
	public void testChangeCancelBooking() {
	    // Call the method and ensure it runs without throwing any exceptions
	    try {
	        WFC.changeCancelBooking();
	    } catch (Exception e) {
	        fail("Unexpected exception thrown: " + e);
	    }
	}
	@Test
	public void testBookLesson() {
	// Initialize the input stream with a test data
	String input = "1\n1\n1\nYoga\n1\n";
	System.setIn(new ByteArrayInputStream(input.getBytes()));
	try {
	    WFC.bookLesson();
	} catch (Exception e) {
	    fail("Unexpected exception thrown: " + e);
	}

	// Check if the lesson is booked successfully
	assertEquals(1, WFC.lesson_bookings[0][0]);
	assertEquals(1, WFC.lesson_customers[0][0]);
	}
	@Test
	public void testAttendLesson() {
	    // Set up input stream to simulate user input
	    ByteArrayInputStream in = new ByteArrayInputStream("123\nGreat lesson!\n5".getBytes());
	    System.setIn(in);

	    // Call the method and ensure it runs without throwing any exceptions
	    try {
	        WFC.attendLesson();
	    } catch (Exception e) {
	        fail("Unexpected exception thrown: " + e);
	    }
	}
	@Test
	public void testMonthlyLessonReport() {
	    // Set up test data
	    WFC.lesson_customers = new int[][] {{5, 3, 2}, {2, 1, 1}, {3, 2, 2}, {4, 3, 1}};
	    WFC.lesson_ratings = new int[][] {{17, 13, 9}, {9, 6, 7}, {11, 10, 6}, {14, 11, 8}};

	    // Call the method and capture the output
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(baos));
	    WFC.monthlyLessonReport();
	    String output = baos.toString();

	    // Verify that the output contains the correct information
	    assertTrue(output.contains("Monthly lesson report for month "));
	    assertTrue(output.contains("Weekend 1:"));
	    assertTrue(output.contains("Weekend 2:"));
	    assertTrue(output.contains("Weekend 3:"));
	    assertTrue(output.contains("Weekend 4:"));
	    assertTrue(output.contains("Beginner:"));
	    assertTrue(output.contains("Intermediate:"));
	    assertTrue(output.contains("Advanced:"));
	    assertTrue(output.contains("average rating: "));
	}
	


}
