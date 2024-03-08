import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class Backend implements BackendInterface {
    private DijkstraGraph<String, Integer> graph;

    /**
     * Constructs a Backend instance with a given graph.
     *
     * @param graph A graph that adheres to the GraphADT interface
     * @throws IllegalArgumentException If the provided graph reference is null
     */
    public Backend(DijkstraGraph<String, Integer> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }
        this.graph = graph;
    }

    @Override
    public void readDataFromFile(String filePath) throws IOException, IllegalArgumentException {
        // Check if the file path is not null or empty
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty.");
        }

        // Clear the current graph before loading new data
        this.graph = new DijkstraGraph<String, Integer>(new PlaceholderMap<>());

        // Open the file and read it line by line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip empty lines and the first line which should be 'graph socialnetwork {'
                if (line.trim().isEmpty() || line.trim().equals("graph socialnetwork {")) {
                    continue;
                }

                // Check for the closing bracket '}' which signifies the end of the file
                if (line.trim().equals("}")) {
                    break;
                }

                // Extract the user IDs from the line
                String[] users = line.split(" -- ");
                if (users.length == 2) {
                    String user1 = users[0].trim().replaceAll("\"", "");
                    String user2 = users[1].trim().replaceAll("\"", "").replace(";", "");

                    // Insert nodes and an edge with a weight of 1 into the graph
                    graph.insertNode(user1);
                    graph.insertNode(user2);
                    graph.insertEdge(user1, user2, 1); // Assuming the graph is undirected and unweighted
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading from the file: " + e.getMessage(), e);
        }
    }

    @Override
    public ShortestPathResultsInterface getClosestConnection(String user1, String user2)
            throws NoSuchElementException, IllegalArgumentException {
        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("User identifiers cannot be null.");
        }
        if (!graph.containsNode(user1) || !graph.containsNode(user2)) {
            throw new NoSuchElementException("One or both users not found in the graph.");
        }

        List<String> path = graph.shortestPathData(user1, user2);
        return new ShortestPathResults(path);
    }

    @Override
    public String getStatistics() throws IllegalStateException {
        // Check if the graph has nodes before calculating statistics
        int numberOfParticipants = graph.getNodeCount();
        if (numberOfParticipants == 0) {
            throw new IllegalStateException("Graph data has not been loaded.");
        }
        int numberOfFriendships = graph.getEdgeCount();
        double averageNumberOfFriends = (double) numberOfFriendships / numberOfParticipants;

        return "Number of participants: " + numberOfParticipants +
                ", Number of friendships: " + numberOfFriendships +
                ", Average number of friends: " + averageNumberOfFriends;
    }

    private class ShortestPathResults implements ShortestPathResultsInterface {
        private final List<String> shortestPath;

        public ShortestPathResults(List<String> shortestPath) {
            this.shortestPath = shortestPath;
        }

        @Override
        public List<String> getShortestPath() {
            return shortestPath;
        }

        @Override
        public int getNumberOfIntermediaries() {
            return Math.max(shortestPath.size() - 2, 0);
        }
    }
    
}
