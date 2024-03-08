// --== CS400 Fall 2023 File Header Information ==--
// Name: James Yin
// Email: xyin63@wisc.edu
// Group: B03
// TA: Zheyang Xiong
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.util.*;
import java.io.File;


public class Frontend implements FrontendInterface {
  BackendInterface backend;
  Scanner scanner = new Scanner(System.in);
  String savedFile = ""; // Keeps track of loaded file name

  /**
   * Constructor that initializes the frontend.
   * 
   * @param backend Reference to the backend.
   * @param scanner Instance of java.util.Scanner to read user input.
   */
  public Frontend(BackendInterface backend, java.util.Scanner scanner) {
    this.backend = backend;
    this.scanner = scanner;
  }

  /**
   * Starts the main command loop where users can now type commands.
   */
  public void startMainCommandLoop() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to Song Searcher, choose an option below by typing the number.");

    // Display the name of the loaded file if a file is successfully loaded
    while (true) {
      if (savedFile != null && !savedFile.isEmpty()) {
        System.out.println("Currently loaded file: " + savedFile);
    }
      System.out.println("Choose an option:");
      System.out.println("1. Load song data");
      System.out.println("2. List songs by danceability");
      System.out.println("3. Find average danceability");
      System.out.println("4. Exit application");

      String input = scanner.nextLine().trim();
      // Allows the command loop to keep looping until user chooses to exit
      switch (input) {
        case "1": // Option 1: Load song data
          System.out.println("Please enter the file path to a valid CSV file:");
          String filePath = scanner.nextLine();
          System.out.println(loadSongData(filePath));
          break;
        case "2": // Option 2: List songs with danceability greater or equal to the desired number.
          System.out.println("Please enter the desired danceability score:");
          double score = scanner.nextDouble();
          scanner.nextLine(); // To consume the newline left after reading double
          List<SingleSongInterface> songs = listSongsByDanceability(score);
          for (SingleSongInterface song : songs) {
            System.out.println(song.getArtist() + ", " + song.getTitle() + ", " + song.getDanceability());
          }
          break;
        case "3": // Option 3: Display the average danceability of songs loaded.
          // If the user has not loaded a song file yet, remind the user to do that first
          if (savedFile == null || savedFile.isEmpty()) {
            System.out.println("No songs have been loaded, please load a song list first.");
          } else {
          System.out.println("Average danceability: " + findAverageDanceability());
          }
          break;
        case "4": // Option 4: Exit the application.
          exitApplication();
          scanner.close(); // Closing the scanner as we're exiting
          return; // Exiting the loop and method
        default:
          System.out.println("Invalid option. Please choose a valid number.");
      }
      System.out.println();
    }
  }

  /**
   * Loads data from a specified file provided by the user.
   * 
   * @param filePath Path of the data file to be loaded.
   * @return A success message or an error message.
   */
  public String loadSongData(String filePath) {
    // Check if the file extension is .csv
    if (!filePath.toLowerCase().endsWith(".csv")) {
      return "Error: File is not a CSV, please provide a valid path to a CSV file. \nReturning to main menu.";
    }
                                                                                                           
    // Check if the file exists and is accessible                                                            
    File file = new File(filePath);                                                                          
    if (!file.exists() || !file.isFile() || !file.canRead()) {                                               
        return "Error: File does not exist or is not readable. Please provide a valid path. \nReturning to main menu.";                                                                                                  
    }                                                                                                        
    
    // Attempt to load the CSV data
    backend.loadSongData(filePath);
    savedFile = filePath;

    return "Successfully loaded song data.";

  }

  /**
   * Lists all songs with a specified danceability score.
   *
   * @param danceabilityScore Desired danceability score.
   * @return List of songs with the given danceability score.
   */
  public List<SingleSongInterface> listSongsByDanceability(double danceabilityScore) {
    // inorderTraversal(root, danceabilityScore, result);
    return backend.listSongsByDanceability(danceabilityScore);
    
  }

  /**
   * Computes and displays the average danceability score of the songs.
   * 
   * @return The average danceability score.
   */
  public double findAverageDanceability() {
    return backend.findAverageDanceability();
  }

  /**
   * Exits the application.
   */
  public void exitApplication() {
    System.out.println("Thank you for using Song Searcher!");
    return;
  }

  /**
   * Computes the command result of the help of the backend. Redundant method from interface.
   */
  public void computeResult() {
    return;
  }

  /*
   * Delivers instructive feedback if the user enters invalid inpyt. Redundant method from interface.
   */
  public void invalidInput() {
    return;
  }

  
  public static void main(String[] args) {
    Backend backendInstance = new Backend();
    
    // Initialize a scanner for user input
    Scanner userInputScanner = new Scanner(System.in);

    // Initialize the frontend with the backend and scanner instances
    Frontend frontendInstance = new Frontend(backendInstance, userInputScanner);
    frontendInstance.startMainCommandLoop();
  }



}
