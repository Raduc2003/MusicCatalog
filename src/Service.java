import DB.SongRepository;
import Models.Album;
import Models.Playlist;
import Models.Song;
import Models.User;

import java.util.ArrayList;
import java.util.List;

public  class Service {

    //storage
    private static ArrayList<User> users;
    private static List<Song> songs;
    private static List<Playlist> playlists;
    private static List<Album> albums;

    //repositories
    private static SongRepository songRepository = SongRepository.getInstance();

    //getter setter


    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        Service.users = users;
    }

    public static List<Song> getSongs() {
        songs = songRepository.getData();
        return songs;
    }

    public static void setSongs(List<Song> songs) {
        Service.songs = songs;
    }

    public static List<Playlist> getPlaylists() {
        return playlists;
    }

    public static void setPlaylists(List<Playlist> playlists) {
        Service.playlists = playlists;
    }

    public static List<Album> getAlbums() {
        return albums;
    }

    public static void setAlbums(List<Album> albums) {
        Service.albums = albums;
    }

    public SongRepository getSongRepository() {
        return songRepository;
    }

    public void setSongRepository(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
}



