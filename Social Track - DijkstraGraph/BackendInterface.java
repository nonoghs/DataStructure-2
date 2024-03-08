import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * This interface defines the backend functionalities for the Social Track
 * application.
 * It allows for reading data from a file into a graph, finding the closest
 * connection
 * between two participants, and getting statistics about the social network.
 *
 * Implementations of this interface are expected to have a constructor
 * with the following signature:
 * IndividualBackendInterface(GraphADT<NodeType, EdgeType> graph) throws
 * IllegalArgumentException
 * if the provided graph reference is null.
 */
public interface BackendInterface {

    /**
     * Reads graph data from a file and initializes the graph data structure.
     *
     * @param filePath the path to the file with the graph data
     * @throws IOException              if there is an error reading from the file
     * @throws IllegalArgumentException if the filePath is null or otherwise invalid
     */
    public void readDataFromFile(String filePath) throws IOException, IllegalArgumentException;

    /**
     * Finds the closest connection (shortest path) between two participants in the
     * graph.
     *
     * @param user1 the identifier for the first participant
     * @param user2 the identifier for the second participant
     * @return an instance of IShortestPathResults containing the path information
     * @throws NoSuchElementException   if one or both participants are not found in
     *                                  the graph
     * @throws IllegalArgumentException if user1 or user2 is null or invalid
     */
    public ShortestPathResultsInterface getClosestConnection(String user1, String user2)
            throws NoSuchElementException, IllegalArgumentException;

    /**
     * Gathers and returns statistics about the social network graph.
     *
     * @return a String containing statistics about the network, such as the number
     *         of participants
     *         (nodes), the number of friendships (edges), and the average number of
     *         friends per participant.
     * @throws IllegalStateException if the graph data has not been loaded
     */
    public String getStatistics() throws IllegalStateException;
}
