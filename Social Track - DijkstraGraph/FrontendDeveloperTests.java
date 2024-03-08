// --== CS400 Project Two File Header Information ==--
// Name: Zihan Xu
// Email: zxu536@wisc.edu
// Group: F38
// TA: Matt
// Lecturer: Gary
// Notes to Grader: I may modify some testers. Cause right now, I return some dummy cases cause it's a placeholder.

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FrontendDeveloperTests {
    /**
     * Test if the application can successfully load a data file and display a success message.
     */
    @Test
    public void testLoadDataFileSuccess() {
        TextUITester tester = new TextUITester("1\nvalidFilePath\n4\n");
        Frontend frontendInstance = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        frontendInstance.startMainLoop();
        String output = tester.checkOutput();
        boolean result = output.contains("Data file successfully loaded.");
        assertTrue(result, "Data file was not loaded successfully when expected.");
    }

    /**
     * Test if the application correctly handles an invalid file path.
     */
    @Test
    public void testLoadDataFileFailure() {
        TextUITester tester = new TextUITester("1\ninvalidFilePath\n4\n");
        Frontend frontendInstance = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        frontendInstance.startMainLoop();
        String output = tester.checkOutput();
        boolean result = output.contains("Failed to load file:");
        assertTrue(result, "Incorrect handling of invalid file path.");
    }

    /**
     * Test if the application can display statistics about the social network.
     */
    @Test
    public void testShowStatistics() {
        // Simulate loading data and then displaying statistics
        TextUITester tester = new TextUITester("1\nvalidFilePath\n2\n4\n");
        Frontend frontendInstance = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        frontendInstance.startMainLoop();
        String output = tester.checkOutput();
        //System.out.println("Captured output: " + output);
        boolean result = output.contains("Nodes: 10, Edges: 20, Average Friends: 5");
        assertTrue(result, "Statistics were not displayed correctly.");
    }


    /**
     * Test if the application correctly handles an invalid command.
     */
    @Test
    public void testInvalidCommand() {
        TextUITester tester = new TextUITester("invalidCommand\n4\n");
        Frontend frontendInstance = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        frontendInstance.startMainLoop();
        String output = tester.checkOutput();
        boolean result = output.contains("Invalid option. Please choose a valid number.");
        assertTrue(result, "Invalid command was not handled correctly.");
    }

    /**
     * Test if the application correctly displays a message when no file is loaded and statistics are requested.
     */
    @Test
    public void testShowStatisticsWithoutFile() {
        TextUITester tester = new TextUITester("2\n4\n");
        Frontend frontendInstance = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        frontendInstance.startMainLoop();
        String output = tester.checkOutput();
        boolean result = output.contains("Error:") || output.contains("No data loaded");
        assertTrue(result, "Statistics display did not handle the absence of a data file correctly.");
    }

    /**
     * Test if the application correctly handles a request for finding the closest connection with invalid participants.
     */
    @Test
    public void testFindClosestConnectionInvalidParticipants() {
        // Create a backend instance that is designed for testing.
        // This can be a mock or a real instance, depending on your setup.
        BackendInterface backendForTest = new BackendPlaceholder();

        // Create a frontend instance with the test backend.
        Frontend frontendInstance = new Frontend(backendForTest, new Scanner(System.in));

        // Call the method directly with invalid participants.
        frontendInstance.getClosestConnection("invalidParticipantOne", "invalidParticipantTwo");

    }

    /**
     * Test if the application correctly handles exiting immediately after starting.
     */
    @Test
    public void testImmediateExit() {
        TextUITester tester = new TextUITester("4\n");
        Frontend frontendInstance = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        frontendInstance.startMainLoop();
        String output = tester.checkOutput();
        boolean result = output.contains("Thank you for using Social Track!");
        assertTrue(result, "Immediate exit was not handled correctly.");
    }

    @Test
    public void testLoadDataAndShowStatisticsIntegration() {
        TextUITester tester = new TextUITester("1\nvalidFilePath\n2\n4\n");
        Frontend frontendInstance = new Frontend(new BackendPlaceholder(), new Scanner(System.in));
        frontendInstance.startMainLoop();
        String output = tester.checkOutput();

        boolean loadDataResult = output.contains("Data file successfully loaded.");
        boolean showStatsResult = output.contains("Nodes: 10, Edges: 20, Average Friends: 5");
        assertTrue(loadDataResult && showStatsResult, "Load and Stats Failed");
    }

    @Test
    public void testReadFromInvalidFilePath() {
        Backend backend = new Backend(new DijkstraGraph<String, Integer>(new PlaceholderMap<>()));
        String invalidFilePath = "./nonexistentfile.dot";
        boolean exceptionOccurred = false;

        try {
            backend.readDataFromFile(invalidFilePath);
        } catch (IOException e) {
            exceptionOccurred = true;
            // Optionally check the exception message
            assertTrue(e.getMessage().contains("Error reading from the file:"), "IOException should be thrown with an appropriate message.");
        }

        assertTrue(exceptionOccurred, "IOException should be thrown for an invalid file path.");
    }

}
