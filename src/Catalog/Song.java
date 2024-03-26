package Catalog;

import java.util.ArrayList;
import java.util.List;

public class Song {
    public int id;
    public String title;
    public String artist;
    public String category;

    private ArrayList<Playlist> playlistsHaveSong;

    public Song(int id, String title, String artist, String category) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
    }

    public ArrayList<Playlist> getPlaylistsHaveSong() {
        return playlistsHaveSong;
    }

    public void setPlaylistsHaveSong(ArrayList<Playlist> playlistsHaveSong) {
        this.playlistsHaveSong = playlistsHaveSong;
    }

    @Override
    public String toString() {
        return "{" +
                "'" +title + "'"  + "by"+
                 artist
                +"}";
    }
}
