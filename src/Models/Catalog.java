package Models;

import game.Leaderboard;

import java.util.ArrayList;
import java.util.List;

//va fii singleton
public class Catalog  {

    private int userId;
    private User user;
    private List<Song> songs;
    private List<Album> albums;
    private List<Playlist> playlists;
    private List<Leaderboard> leaderboards;
    private List<Song> spotifySongs;
    private static Catalog catalogInstc=null;

    private Catalog(){}


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public List<Leaderboard> getLeaderboards() {
        return leaderboards;
    }

    public void setLeaderboards(List<Leaderboard> leaderboards) {
        this.leaderboards = leaderboards;
    }

    public List<Song> getSpotifySongs() {
        return spotifySongs;
    }

    public void setSpotifySongs(List<Song> spotifySongs) {
        this.spotifySongs = spotifySongs;
    }

    public static Catalog getInstance() {
        if(catalogInstc==null){
            catalogInstc = new Catalog();
        }
        return catalogInstc;
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
