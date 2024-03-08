// --== CS400 Fall 2023 File Header Information ==--
// Name: Zihan Xu
// Email: zxu536@wisc.edu
// Group: B03
// TA: Zheyang Xiong
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Backend class that implements BackendInterface. This class is responsible for managing song data
 * and providing functionality to load song data from a CSV file, find average danceability of songs,
 * and list songs based on their danceability.
 */
public class Backend implements BackendInterface {

    // Red-Black Tree to store songs. Each song is a SingleSong object.
    private IterableMultiKeyRBT<SingleSong> songs = new IterableMultiKeyRBT<>();

    /**
     * Loads song data from a specified CSV file.
     *
     * @param filePath The path to the CSV file.
     */
    public void loadSongData(String filePath) {
        try {
            // Create a Scanner to read from the file
            Scanner scanner = new Scanner(new File(filePath));
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip the header line of the CSV file
            }

            // Read each line of the CSV file
            while (scanner.hasNextLine()) {
                String currLine = scanner.nextLine();
                // Parse the current line to extract song data
                List<String> data = parseCsvLine(currLine);

                // Ensure that there is enough data to create a SingleSong object
                if (data.size() >= 7) {
                    // Extract song data
                    String artist = data.get(1);
                    String title = data.get(0);
                    int year = Integer.parseInt(data.get(3));
                    String genre = data.get(2);
                    int danceability = Integer.parseInt(data.get(6));
                    // Create a SingleSong object and add it to the songs tree
                    SingleSong song = new SingleSong(artist, title, year, genre, danceability);
                    songs.insertSingleKey(song);
                }
            }
        } catch (FileNotFoundException e) {
            // Handle the case where the file is not found
            System.err.println("File not found: " + filePath);
        }
    }

    /**
     * Parses a line from a CSV file.
     *
     * @param line The line to parse.
     * @return A list of strings representing the values in the line.
     */
    private List<String> parseCsvLine(String line) {
        List<String> values = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder buffer = new StringBuilder();
        // Iterate through each character in the line
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes; // Toggle state if a quote is encountered
            } else if (c == ',' && !inQuotes) {
                // Add the current value to the list if a comma is encountered outside quotes
                values.add(buffer.toString().trim());
                buffer = new StringBuilder();
            } else {
                // Append the current character to the buffer
                buffer.append(c);
            }
        }
        // Add the last field to the list
        values.add(buffer.toString().trim());
        return values;
    }

    /**
     * Finds the average danceability of all songs.
     *
     * @return The average danceability as an integer.
     */
    @Override
    public int findAverageDanceability() {
        double total = 0;
        int count = 0;
        // Iterate through all songs and sum their danceability
        for (SingleSong song : songs) {
            total += song.getDanceability();
            count++;
        }
        // Calculate and return the average danceability
        if (count > 0) {
            return (int)(total / count);
        } else {
            return 0;
        }
    }

    /**
     * Lists all songs with a danceability score greater than or equal to the specified minimum.
     *
     * @param minDanceability The minimum danceability score.
     * @return A list of songs that meet the criteria.
     */
    @Override
    public List<SingleSongInterface> listSongsByDanceability(double minDanceability) {
        List<SingleSongInterface> resultSongs = new ArrayList<>();
        // Iterate through all songs and add those with sufficient danceability to the result list
        for (SingleSong song : songs) {
            if (song.getDanceability() >= minDanceability) {
                resultSongs.add(song);
            }
        }
        return resultSongs;
    }

    /**
     * Main method to run the program.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Instantiate the Backend
        Backend backend = new Backend();

        // Initialize a scanner for user input
        Scanner userInputScanner = new Scanner(System.in);

        // Instantiate the Frontend and pass the Backend reference and scanner to it
        Frontend frontend = new Frontend(backend, userInputScanner);

        // Start the main command loop of the Frontend
        frontend.startMainCommandLoop();
    }
}
