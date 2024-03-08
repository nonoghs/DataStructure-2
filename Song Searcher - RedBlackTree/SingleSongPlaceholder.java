import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SingleSongPlaceholder implements SingleSongInterface {
    String artist;
    String title;
    int year;
    String genre;
    int danceability;
    public SingleSongPlaceholder(String artist, String title, int danceability, int year, String genre) {
        this.artist = artist;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.danceability = danceability;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public int getDanceability() {
        return danceability;
    }

    @Override
    public int compareTo(SingleSong singleSong) {
        return 0;
    }
}