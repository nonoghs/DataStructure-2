import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * This class contains JUnit tests for the BackendInterface implementation.
 * The tests are designed to be opaque-box.
 */
public class BackendDeveloperTests {

    private BackendInterface backend;

    // Initialize necessary paths and dummy users for testing purposes
    private final String validFilePath = "./socialnetwork.dot";
    private final String invalidFilePath = "./socialnetworkinvalid.dot";
    private final String user1 = "user17";
    private final String user2 = "user29";
    private final String nonExistentUser = "noneuser";

    /**
     * Setup method to initialize the backend interface with a valid graph.
     * Assumes the existence of a default constructor and a method to read data from
     * a file.
     * 
     * @throws IOException if the file path is invalid or data cannot be read.
     */
    @BeforeEach
    public void setUp() throws IOException {
        // The specific BackendInterface implementation is instantiated here
        backend = new Backend(new DijkstraGraph<String, Integer>(new PlaceholderMap<>()));
        backend.readDataFromFile(validFilePath);
    }

    /**
     * Test reading valid graph data from a file.
     * This test ensures that the backend can correctly read and parse data
     * from a properly formatted file without throwing exceptions.
     */
    @Test
    public void testReadValidDataFromFile() {
        assertDoesNotThrow(() -> backend.readDataFromFile(validFilePath));
    }

    /**
     * Test reading data from an invalid file path.
     * This test ensures that the backend throws an IOException when attempting
     * to read from an invalid file path, which is expected behavior.
     */
    @Test
    public void testReadInvalidDataFromFile() {
        assertThrows(IOException.class, () -> backend.readDataFromFile(invalidFilePath));
    }

    /**
     * Test finding the closest connection between two valid users.
     * This test checks if the method correctly computes the shortest path
     * between two users who exist in the graph without throwing any exceptions.
     */
    @Test
    public void testGetClosestConnectionWithValidUsers() {
        assertDoesNotThrow(() -> backend.getClosestConnection(user1, user2));
        assertNotNull(backend.getClosestConnection(user1, user2));
    }

    /**
     * Test finding a connection where one user does not exist.
     * This test validates that the method throws a NoSuchElementException when
     * trying to find a path involving a non-existent user.
     */
    @Test
    public void testGetClosestConnectionWithNonExistentUser() {
        assertThrows(NoSuchElementException.class, () -> backend.getClosestConnection(user1, nonExistentUser));
    }

    /**
     * Test retrieving statistics without loading graph data.
     * This test ensures that the method throws an IllegalStateException when
     * trying to get statistics before any graph data has been loaded.
     */
    @Test
    public void testGetStatisticsWithoutDataLoaded() {
        // Create a new backend instance without loading any data
        BackendInterface newBackend = new Backend(new DijkstraGraph<String, Integer>(new PlaceholderMap<>()));

        // Attempt to get statistics, which should fail because no data has been loaded
        assertThrows(IllegalStateException.class, newBackend::getStatistics);
    }

    /**
     * Test the cases that two users are the same user.
     */
    @Test
    public void testGetClosestConnectionWithSameUser() {
        // Check the connection from a user to themselves
        ShortestPathResultsInterface results = backend.getClosestConnection(user1, user1);
        assertNotNull(results);
        assertEquals(0, results.getNumberOfIntermediaries(),
                "The number of intermediaries should be 0 for the same user.");
    
        // The shortest path should contain only one node, which is the user itself
        assertEquals(1, results.getShortestPath().size(), "The shortest path should contain only one node for the same user.");
        assertEquals(user1, results.getShortestPath().get(0), "The shortest path should contain the user itself.");
    }
}
