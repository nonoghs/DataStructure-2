import java.util.List;

/**
 * This interface defines the basic backend functionality for the SongSearcher app
 * */
public interface BackendInterface {
    /**
     * Reads in a set of songs from a CSV file
     * @param filePath The path to the CSV file from which song data will be loaded
     */
    public void loadSongData(String filePath);

    /**
     * Calculate and return average danceability of all songs
     * @return Average danceability score
     */
    public int findAverageDanceability();

    /**
     * Finds and returns a list of songs with minimum danceability that is at or above a threshold.
     *
     * @param minDanceability The minimum danceability score
     * @return List of songs at or above the threshold
     */
    public List<SingleSongInterface> listSongsByDanceability(double minDanceability);
}
