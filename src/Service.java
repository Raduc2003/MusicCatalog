import DB.SongRepository;
import DB.UserRepository;
import Models.Album;
import Models.Playlist;
import Models.Song;
import Models.User;

import java.util.ArrayList;
import java.util.List;

public  class Service {

    //storage
    private static User user;
    private static List<Song> songs;
    private static List<Playlist> playlists;
    private static List<Album> albums;

    //repositories
    private static SongRepository songRepository = SongRepository.getInstance();
    private static UserRepository userRepository = UserRepository.getInstance();
    //getter setter


    public static User getUser(String email,String password) {
        return userRepository.getUser(email,password);
    }

    public static void addUser(String email,String password) {
        userRepository.addUser(email,password);
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



