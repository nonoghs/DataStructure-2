/**
 * Interface representing individual attributes of a song.
 */
public interface SingleSongInterface {

    /**
     * Retrieves the artist of the song.
     * @return Artist name.
     */
    public String getArtist();

    /**
     * Retrieves the title of the song.
     * @return Song title.
     */
    public String getTitle();

    /**
     * Retrieves the release year of the song.
     * @return Release year.
     */
    public int getYear();

    /**
     * Retrieves the genre of the song.
     * @return Song genre.
     */
    public String getGenre();

    /**
     * Retrieves the danceability score of the song.
     * @return Danceability score.
     */
    public int getDanceability();

    int compareTo(SingleSong singleSong);
}
