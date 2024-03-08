import java.util.*;

public interface FrontendInterface {
  /**
   * Starts the main command loop where users can now type commands.
   */
  public void startMainCommandLoop();
  
  /**
   * Loads data from a specified file provided by the user.
   * 
   * @param filePath Path of the data file to be loaded.
   * @return A success message or an error message.
   */
  public String loadSongData(String filePath);
  
  /**
   * Lists all songs with a specified danceability score.
   *
   * @param danceabilityScore Desired danceability score.
   * @return List of songs with the given danceability score.
   */
  public List<SingleSongInterface> listSongsByDanceability(double danceabilityScore);
  
  /**
   * Computes and displays the average danceability score of the songs.
   * 
   * @return The average danceability score.
   */
  public double findAverageDanceability();
  
  /**
   * Exits the application.
   */
  public void exitApplication();
  
  /**
   * Computes the command result of the help of the backend.
   */
  public void computeResult();
  
  /*
   * Delivers instructive feedback if the user enters invalid inpyt.
   */
  public void invalidInput();
  
  /**
   * Constructor that initializes the frontend.
   * 
   * @param backend Reference to the backend.
   * @param scanner Instance of java.util.Scanner to read user input.
   */
  // public ClassName(Backend backend, java.util.Scanner scanner);
}
