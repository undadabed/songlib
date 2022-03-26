// GROUP MEMBERS
// Jonathan Fan
// Ryan Hsiu
package app;

public class Song {
    private String song;
    private String artist;
    private int year;
    private String album;

    public Song(){
        setSong("");
        setArtist("");
        setYear(-1);
        setAlbum("");
    }

    public Song(String song, String artist) {
        this.setArtist(artist);
        this.setSong(song);
        setYear(0);
        setAlbum("");
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String toString() {
        return song + " (by " + artist + ")";
    }
}
