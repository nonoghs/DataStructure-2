import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class BackendPlaceholder implements BackendInterface {

    private boolean isDataLoaded = true;

    // Constructor
    public BackendPlaceholder() {
        // Initialize with minimal setup
    }

    @Override
    public void readDataFromFile(String filePath) throws IOException {
        // Simulate data loading. You can add conditions to simulate different scenarios.
        // For example, setting isDataLoaded to true only if filePath is a specific string.
        if (filePath.equals("validFilePath")) {
            isDataLoaded = true;
        } else {
            throw new IOException("Invalid file path provided");
        }
    }

    @Override
    public ShortestPathResultsInterface getClosestConnection(String user1, String user2)
            throws NoSuchElementException, IllegalArgumentException {
        // Check for invalid participants
        if (user1.equals("invalidParticipantOne") || user2.equals("invalidParticipantTwo")) {
            throw new NoSuchElementException("Participants not found");
        }

        // Returns a dummy shortest path result for valid participants
        return new ShortestPathResultsPlaceholder();
    }


    @Override
    public String getStatistics() throws IllegalStateException {
        // Check if data is loaded
        if (!isDataLoaded) {
            throw new IllegalStateException("No data loaded");
        }

        // Return dummy statistics string if data is loaded
        return "Nodes: 10, Edges: 20, Average Friends: 5";
    }

    // Dummy implementation of ShortestPathResultsInterface
    private static class ShortestPathResultsPlaceholder implements ShortestPathResultsInterface {
        @Override
        public List<String> getShortestPath() {
            return null;
        }

        @Override
        public int getNumberOfIntermediaries() {
            return 0;
        }

        @Override
        public String toString() {
            // This is a placeholder string. Modify it as needed for your actual implementation.
            return "Shortest path between participantOne and participantTwo";
        }
        // Implement methods as per the interface (returning dummy values)
    }
}
