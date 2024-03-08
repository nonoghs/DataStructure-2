import java.util.List;

/**
 * This interface defines the methods to access the results of a shortest path search
 * within a social network graph. It provides access to the shortest path between two
 * participants and the number of intermediary friends on that path.
 *
 * Note: Implementations of this interface are expected to have a constructor with the following signature:
 * IShortestPathResults(List<String> shortestPath)
 */
public interface ShortestPathResultsInterface {

    /**
     * Retrieves the list of participants representing the shortest path
     * between two participants in the social network graph.
     *
     * @return a List of participants (as Strings) forming the shortest path
     */
    public List<String> getShortestPath();

    /**
     * Retrieves the number of intermediary friends in the shortest path
     * between two participants.
     *
     * @return the number of intermediary friends on the path
     */
    public int getNumberOfIntermediaries();
}
