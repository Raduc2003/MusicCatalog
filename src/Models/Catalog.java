package Models;

import java.util.ArrayList;
import java.util.List;

//va fii singleton
public class Catalog  {

    private int userId;
    private List<Song> songs;
    private List<Album> albums;
    private List<Playlist> playlists;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "userId=" + userId +
                ", songs=" + songs +
                ", albums=" + albums +
                ", playlists=" + playlists +
                '}';
    }
}
