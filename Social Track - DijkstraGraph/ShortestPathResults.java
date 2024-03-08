import java.util.List; 

/**
 * This class implements the ShortestPathResultsInterface and is used to
 * represent
 * the results of a shortest path search in a social network graph.
 * It stores the shortest path between two nodes (users) and provides methods to
 * access this path and the number of intermediaries in it.
 */
public class ShortestPathResults implements ShortestPathResultsInterface {
    private final List<String> shortestPath;

    /**
     * Constructs a new ShortestPathResults object with the given shortest path.
     *
     * @param shortestPath The list of nodes (user IDs) representing the shortest
     *                     path
     *                     between two users in the social network.
     */
    public ShortestPathResults(List<String> shortestPath) {
        this.shortestPath = shortestPath;
    }

    /**
     * Retrieves the list of participants (user IDs) representing the shortest path
     * between two users in the social network graph.
     *
     * @return A list of user IDs forming the shortest path.
     */
    @Override
    public List<String> getShortestPath() {
        return shortestPath;
    }

    /**
     * Retrieves the number of intermediary friends (excluding the start and end
     * users)
     * in the shortest path between two users.
     * If the start and end users are the same, or if there are no intermediaries,
     * this method returns 0.
     *
     * @return The number of intermediary friends on the shortest path.
     */
    @Override
    public int getNumberOfIntermediaries() {
        // Subtract 2 to exclude the start and end nodes from the count of
        // intermediaries
        return Math.max(shortestPath.size() - 2, 0);
    }
}
