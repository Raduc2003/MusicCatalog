package Models;

import java.util.ArrayList;

public class Song {

    public int id;
    public String title;

    public String artist;
    public String category;

    private ArrayList<Playlist> inPlaylists;

    public Song(int id, String title, String artist, String category) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
    }

    public ArrayList<Playlist> getInPlaylists() {
        return inPlaylists;
    }

    public void setInPlaylists(ArrayList<Playlist> inPlaylists) {
        this.inPlaylists = inPlaylists;
    }

    @Override
    public String toString() {
        return "{" +
                "'" +title + "'"  + "by"+
                 artist
                +"}";
    }
}
