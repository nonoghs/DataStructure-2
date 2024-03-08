
public class SingleSong implements SingleSongInterface, Comparable<SingleSong> {
    private String artist;
    private String title;
    private int year;
    private String genre;
    private int danceability;

    // Constructor to initialize properties
    public SingleSong(String artist, String title, int year, String genre, int danceability) {
        this.artist = artist;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.danceability = danceability;
    }

    @Override
    public String getArtist() {
        return this.artist;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public String getGenre() {
        return this.genre;
    }

    @Override
    public int getDanceability() {
        return this.danceability;
    }


    @Override
    public int compareTo(SingleSong singleSong) {
        // First, we compare the artists of the two songs.
        int compareArtist = this.artist.compareTo(singleSong.artist);

        // If the artists are not the same, we return the result of the artist comparison.
        if (compareArtist != 0) {
            return compareArtist;
        }

        // If the artists are the same, we proceed to compare the titles of the songs.
        int compareName = this.title.compareTo(singleSong.title);

        // If the titles are not the same, we return the result of the title comparison.
        if (compareName != 0) {
            return compareName;
        }

        // If both the artists and titles are the same, we compare the release years of the songs.
        // We return the result of the year comparison.
        return Integer.compare(this.year, singleSong.year);
    }

}
