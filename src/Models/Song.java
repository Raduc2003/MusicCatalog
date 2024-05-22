package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Song {

    protected int id;
    protected String title;

    protected String artist;
    protected String category;

    private List<Playlist> inPlaylists;

    public Song(int id, String title, String artist, String category) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
    }
    public Song(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Playlist> getInPlaylists() {
        return inPlaylists;
    }

    public void setInPlaylists(List<Playlist> inPlaylists) {
        this.inPlaylists = inPlaylists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(title, song.title) && Objects.equals(artist, song.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist);
    }

    @Override
    public String toString() {
        return "{" +
                "'" +title + "'"  + "by"+
                 artist
                +"}";
    }
}
