import java.util.*;
import java.util.Scanner;

public class Frontend implements FrontendInterface {
    BackendInterface backend;
    Scanner scanner;
    String loadedFile = ""; // Keeps track of the loaded file name

    /**
     * Constructor that initializes the frontend.
     *
     * @param backend Reference to the backend.
     * @param scanner Instance of java.util.Scanner to read user input.
     */
    public Frontend(BackendInterface backend, Scanner scanner) {
        this.backend = backend;
        this.scanner = scanner;
    }

    /**
     * Starts the main command loop for user interaction.
     */
    public void startMainLoop() {
        System.out.println("Welcome to Social Track, choose an option below by typing the number.");

        while (true) {
            if (!loadedFile.isEmpty()) {
                System.out.println("Currently loaded file: " + loadedFile);
            }
            System.out.println("Choose an option:");
            System.out.println("1. Load data file");
            System.out.println("2. Show statistics");
            System.out.println("3. Find closest connection");
            System.out.println("4. Exit application");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.println("Please enter the file path:");
                    String filePath = scanner.nextLine();
                    loadDataFile(filePath);
                    break;
                case "2":
                    showStatistics();
                    break;
                case "3":
                    findClosestConnectionPrompt();
                    break;
                case "4":
                    exitApp();
                    return; // Exiting the loop and method
                default:
                    System.out.println("Invalid option. Please choose a valid number.");
            }
            System.out.println();
        }
    }

    /**
     * Loads data from the specified file.
     *
     * @param filePath Path of the data file to be loaded.
     */
    public void loadDataFile(String filePath) {
        try {
            backend.readDataFromFile(filePath);
            loadedFile = filePath;
            System.out.println("Data file successfully loaded.");
        } catch (Exception e) {
            System.out.println("Failed to load file: " + e.getMessage());
        }
    }


    /**
     * Shows statistics about the network.
     */
    public void showStatistics() {
        if (loadedFile == null || loadedFile.isEmpty()) {
            System.out.println("No data loaded. Please load a data file first.");
            return;
        }
        try {
            String stats = backend.getStatistics();
            System.out.println(stats);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    /**
     * Prompts the user for two participants and displays the shortest path between them.
     */
    public void findClosestConnectionPrompt() {
        if (loadedFile == null || loadedFile.isEmpty()) {
            System.out.println("No data loaded. Please load a data file first.");
            return;
        }
        System.out.println("Enter the first participant:");
        String participantOne = scanner.nextLine();
        System.out.println("Enter the second participant:");
        String participantTwo = scanner.nextLine();
        ShortestPathResultsInterface results = backend.getClosestConnection(participantOne, participantTwo);
        System.out.println(results.getShortestPath().toString());
    }

    /**
     * Implements the method from the FrontendInterface to find and display the shortest path between two participants.
     *
     * @param participantOne The first participant's identifier.
     * @param participantTwo The second participant's identifier.
     */
    public void getClosestConnection(String participantOne, String participantTwo) {
        try {
            ShortestPathResultsInterface results = backend.getClosestConnection(participantOne, participantTwo);
            System.out.println(results.toString());
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Exits the application.
     */
    public void exitApp() {
        System.out.println("Thank you for using Social Track!");
    }


    public static void main(String[] args) {
        // Create the graph and frontend instances, then start the interface loop.
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        BackendInterface backend = new Backend(graph); // Replace 'Backend' with your actual class name implementing BackendInterface
        FrontendInterface frontend = new Frontend(backend, new Scanner(System.in)); // Replace 'Frontend' with your actual class name implementing FrontendInterface
        frontend.startMainLoop();
    }
}
