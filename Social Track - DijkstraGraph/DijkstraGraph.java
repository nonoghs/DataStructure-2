// --== CS400 File Header Information ==--
// Name: <Zihan Xu>
// Email: <zxu536@wisc.edu>
// Group and Team: <F38>
// Group TA: <Matt>
// Lecturer: <Gary>
// Notes to Grader:<What is the group color???>

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;


/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     * @param map the map that the graph uses to map a data object to the node
     *        object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        PriorityQueue<SearchNode> nodeQueue = new PriorityQueue<>();
        PlaceholderMap<NodeType, Double> shortestPaths = new PlaceholderMap<>();
        Set<NodeType> visited = new HashSet<>();

        Node startNode = this.nodes.get(start);
        if (startNode == null) {
            throw new NoSuchElementException("Start node not found in the graph");
        }

        Node endNode = this.nodes.get(end);
        if (endNode == null) {
            throw new NoSuchElementException("End node not found in the graph");
        }

        nodeQueue.add(new SearchNode(startNode, 0, null));
        shortestPaths.put(start, 0.0);  // Initialize with starting node with a path cost of 0.

        while (!nodeQueue.isEmpty()) {
            SearchNode current = nodeQueue.poll();
            NodeType currentNodeData = current.node.data;

            // If the current node is the end node, return the SearchNode
            if (currentNodeData.equals(end)) {
                return current;
            }

            // If the node has already been visited, skip processing
            if (!visited.add(currentNodeData)) {
                continue;
            }

            // Process each adjacent node
            for (BaseGraph<NodeType, EdgeType>.Edge edge : current.node.edgesLeaving) {
                Node adjacentNode = edge.successor;
                double pathCost = current.cost + edge.data.doubleValue();

                boolean isShorterPathFound = false;
                Double previousCost;
                try {
                    previousCost = shortestPaths.get(adjacentNode.data);
                    if (pathCost < previousCost) {
                        isShorterPathFound = true;
                    }
                } catch (NoSuchElementException e) {
                    // This means the adjacent node is not in the shortestPaths map yet, so this is the shortest path so far.
                    isShorterPathFound = true;
                }

                if (isShorterPathFound) {
                    shortestPaths.put(adjacentNode.data, pathCost);
                    nodeQueue.add(new SearchNode(adjacentNode, pathCost, current));
                }
            }
        }

        throw new NoSuchElementException("No path exists between the start and end nodes");
    }





    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // Attempt to find the shortest path using Dijkstra's algorithm.
        SearchNode endNode = computeShortestPath(start, end);

        // If the end node is not found or there is no path, throw an exception.
        if (endNode == null) {
            throw new NoSuchElementException("No path exists between the start and end nodes.");
        }

        // Create a list to store the path data in reverse order (from end to start).
        LinkedList<NodeType> path = new LinkedList<>();

        // Backtrack from the end node to the start node.
        for (SearchNode current = endNode; current != null; current = current.predecessor) {
            path.addFirst(current.node.data);
        }

        return path;
    }



    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // Use the calculateShortestRoute method to get the shortest path's end node.
        SearchNode endNode = computeShortestPath(start, end);

        // If the end node is null, it means there's no path, so throw an exception.
        if (endNode == null) {
            throw new NoSuchElementException("No path exists between the start and end nodes.");
        }

        // Return the cost associated with the end node, which represents the shortest path cost.
        return endNode.cost;
    }



    // TODO: implement 3+ tests in step 4.1


    @Test
    public void testDirectConnection() {
        DijkstraGraph<Integer, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode(1);
        graph.insertNode(2);
        graph.insertEdge(1, 2, 10);

        SearchNode result = (SearchNode) graph.computeShortestPath(1, 2);

    }

    @Test
    public void testNoPath() {
        DijkstraGraph<Integer, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode(1);
        graph.insertNode(2);

        assertThrows(NoSuchElementException.class, () -> {
            graph.computeShortestPath(1, 2);
        });
    }

    @Test
    public void testMultiplePaths() {
        DijkstraGraph<Integer, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode(1);
        graph.insertNode(2);
        graph.insertNode(3);
        graph.insertEdge(1, 2, 5);
        graph.insertEdge(2, 3, 5);
        graph.insertEdge(1, 3, 20);

        SearchNode result = (SearchNode) graph.computeShortestPath(1, 3);

    }

    @Test
    public void testCycles() {
        DijkstraGraph<Integer, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode(1);
        graph.insertNode(2);
        graph.insertNode(3);
        graph.insertEdge(1, 2, 10);
        graph.insertEdge(2, 3, 10);
        graph.insertEdge(3, 1, 10);

        SearchNode result = (SearchNode) graph.computeShortestPath(1, 3);

    }





}
