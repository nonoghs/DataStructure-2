import java.util.Scanner;

public interface IndividualFrontendInterface {

    /**
     * Constructor that initializes the frontend.
     *
     * @param backend Reference to the backend.
     * @param scanner Instance of java.util.Scanner to read user input.
     */
    //public FrontendInterface(BackendInterface backend, java.util.Scanner scanner) {

    //}
    /**
     * Begins the interactive command loop, prompting the user to select and execute commands.
     */
    public void startMainLoop();

    /**
     * Loads a data file into the application.
     * @param filepath The path of the data file to load.
     */
    public void loadDataFile(String filepath);

    /**
     * Shows statistics about the current dataset, including number of nodes, number of edges, and average number of friends.
     */
    public void showStatistics();

    /**
     * Finds and displays the shortest path between two participants, including all intermediary nodes.
     * @param participantOne The first participant's identifier.
     * @param participantTwo The second participant's identifier.
     */
    public void getClosestConnection(String participantOne, String participantTwo);

    /**
     * Ends the application command loop and exits the application.
     */
    public void exitApp();
}
