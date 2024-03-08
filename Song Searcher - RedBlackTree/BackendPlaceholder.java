import java.util.List;
import java.util.ArrayList;

public class BackendPlaceholder implements BackendInterface {
    ArrayList<SingleSongPlaceholder> songList = new ArrayList<SingleSongPlaceholder>();

    /**
     * Reads in a set of songs from a CSV file
     * @param filePath The path to the CSV file from which song data will be loaded
     */
    public void loadSongData(String filePath) {
        songList.add(new SingleSongPlaceholder("Train", "\"Hey, Soul Sister\"", 67, 2010, "neo mellow"));
        songList.add(new SingleSongPlaceholder("Eminem", "Love The Way You Lie", 75, 2010, "detroit hip hop"));
        songList.add(new SingleSongPlaceholder("Kesha", "TiK ToK", 76, 2010, "dance pop"));
    }

    /**
     * Calculate and return average danceability of all songs
     * @return Average danceability score
     */
    public int findAverageDanceability() {
        int sum = 0;
        for (int i = 0; i < songList.size(); i++) {
            sum += songList.get(i).danceability;
        }
        return sum / songList.size();
    }
    /**
     * Finds and returns a list of songs with minimum danceability that is at or above a threshold.
     *
     * @param minDanceability The minimum danceability score
     * @return List of songs at or above the threshold
     */
    public List<SingleSongInterface> listSongsByDanceability(double minDanceability) {
        List<SingleSongInterface> songListbyDance = new ArrayList<SingleSongInterface>();
        for (int i = 0; i < this.songList.size(); i++) {
            if (this.songList.get(i).danceability >= minDanceability) {
                songListbyDance.add(songList.get(i));
            }
        }
        return songListbyDance;
    }
}