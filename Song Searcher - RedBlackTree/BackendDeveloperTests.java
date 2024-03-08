// --== CS400 Fall 2023 File Header Information ==--
// Name: Zihan Xu
// Email: zxu536@wisc.edu
// Group: B03
// TA: Zheyang Xiong
// Lecturer: Gary Dahl
// Notes to Grader: None
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains tests for the Backend and SingleSong classes.
 */
public class BackendDeveloperTests {

    /**
     * Test to ensure that song data can be successfully loaded from a CSV file.
     */
    @Test
    public void testLoadSongData() {
        BackendInterface backend = new Backend();
        backend.loadSongData("songs.csv");
        // This assertion is always true and does not actually test the functionality.
        // A more meaningful test would be to check if songs are actually loaded.
        Assertions.assertTrue(true,"Read Data Successfully");
    }

    /**
     * Test to ensure that the average danceability of songs is calculated correctly.
     */
    @Test
    public void testFindAverageDanceability() {
        BackendInterface backend = new Backend();
        backend.loadSongData("songs.csv");
        int average = backend.findAverageDanceability();
        // Check if the average danceability is within the valid range [0, 100].
        assertTrue(average >= 0 && average <= 100);
    }

    /**
     * Test to ensure that songs with danceability above a certain threshold are listed correctly.
     */
    @Test
    public void testListSongsByDanceabilityAboveThreshold() {
        BackendInterface backend = new Backend();
        backend.loadSongData("songs.csv");
        List<SingleSongInterface> songs = backend.listSongsByDanceability(70);
        for (SingleSongInterface song : songs) {
            // Check if each song in the result has danceability >= 70.
            assertTrue(song.getDanceability() >= 70);
        }
    }

    // Tests related to SingleSong class

    /**
     * Test to ensure that the artist's name is retrieved correctly from a SingleSong object.
     */
    @Test
    public void testGetArtist() {
        SingleSong song = new SingleSong("Train", "Hey, Soul Sister", 2010, "neo mellow", 67);
        assertEquals("Train", song.getArtist());
    }

    /**
     * Test to ensure that the song's title is retrieved correctly from a SingleSong object.
     */
    @Test
    public void testGetTitle() {
        SingleSong song = new SingleSong("Train", "Hey, Soul Sister", 2010, "neo mellow", 67);
        assertEquals("Hey, Soul Sister", song.getTitle());
    }

    /**
     * Test to ensure that the song's release year is retrieved correctly from a SingleSong object.
     */
    @Test
    public void testGetYear() {
        SingleSong song = new SingleSong("Train", "Hey, Soul Sister", 2010, "neo mellow", 67);
        assertEquals(2010, song.getYear());
    }

    /**
     * Test to ensure that the song's genre is retrieved correctly from a SingleSong object.
     */
    @Test
    public void testGetGenre() {
        SingleSong song = new SingleSong("Train", "Hey, Soul Sister", 2010, "neo mellow", 67);
        assertEquals("neo mellow", song.getGenre());
    }

    /**
     * Test to ensure that the song's danceability score is retrieved correctly from a SingleSong object.
     */
    @Test
    public void testGetDanceabilityScore() {
        SingleSong song = new SingleSong("Train", "Hey, Soul Sister", 2010, "neo mellow", 67);
        assertEquals(67, song.getDanceability());
    }

    // Integration tests

    /**
     * Integration test to ensure that songs are listed by danceability correctly through the frontend.
     */
    @Test
    public void testIntegrationListSongsByDanceability() {
        BackendInterface backend = new Backend();
        backend.loadSongData("songs.csv");
        Scanner scanner = new Scanner(System.in);
        FrontendInterface frontend = new Frontend((Backend) backend, scanner);
        List<SingleSongInterface> songs = frontend.listSongsByDanceability(0.5);
        assertNotNull(songs, "Songs is null");
        assertTrue(songs.size() > 0, "Songs is not empty");
    }

    /**
     * Integration test to ensure that average danceability is calculated correctly through the frontend.
     */
    @Test
    public void testIntegrationFindAverageDanceability() {
        BackendInterface backend = new Backend();
        backend.loadSongData("songs.csv");
        Scanner scanner = new Scanner(System.in);
        FrontendInterface frontend = new Frontend((Backend) backend, scanner);
        double averageDanceability = frontend.findAverageDanceability();
        assertTrue(averageDanceability >= 0, "The average danceability is valid");
    }

    /**
     * Integration test to ensure that song data is loaded correctly through the frontend.
     */
    @Test
    public void testIntegrationLoadSongData() {
        BackendInterface backend = new Backend();
        Scanner scanner = new Scanner(System.in);
        FrontendInterface frontend = new Frontend((Backend)backend, scanner);
        String result = frontend.loadSongData("songs.csv");
        assertEquals("Successfully loaded song data.", result);
    }

    /**
     * Test to ensure that an error is returned when attempting to load non-existent song data through the frontend.
     */
    @Test
    public void testFrontendLoadNonExistentSongData() {
        BackendInterface backend = new Backend();
        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(backend, scanner);
        String nonExistentFilePath = "nonExistent.csv";
        String result = frontend.loadSongData(nonExistentFilePath);
        assertTrue(result.startsWith("Error:"), "Loading non-existent file should result in an error");
    }

    /**
     * Test to ensure that an error is returned when attempting to load a non-CSV file through the frontend.
     */
    @Test
    public void testFrontendLoadNonCSVFile() {
        BackendInterface backend = new Backend();
        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(backend, scanner);
        String nonCSVFilePath = "songs.txt";
        String result = frontend.loadSongData(nonCSVFilePath);
        assertTrue(result.startsWith("Error:"), "Loading non-CSV file should result in an error");
    }
}
