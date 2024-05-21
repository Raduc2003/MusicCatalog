package Services;

import DB.*;
import Models.*;
import game.Match;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public  class Service {

    //storage

    private static  Catalog userCatalog =Catalog.getInstance();
    //repositories
    private static final SongRepository songRepository = SongRepository.getInstance();

    private  static  final SpotifyWrapper spotifyWrapper = SpotifyWrapper.getInstance();
    private static final AlbumRepository albumRepository = AlbumRepository.getInstance();

    private static final UserRepository userRepository = UserRepository.getInstance();

    private static final PlaylistRepository playlistRepository = PlaylistRepository.getInstance();
    //getter setter

//
//
//
    public static User logUser(String email, String password) {
        User user;
        user = userRepository.getUser(email,password);
        userCatalog.setUserId(user.getId());
        userCatalog.setUser(user);
        return  user;
    }

    public static void spotifyLogin() throws IOException {
        spotifyWrapper.authenticate();
        userCatalog.setSpotifyToken(spotifyWrapper.getAccessToken());
    }
    public static void getSpotifySongs() throws IOException {
        List<Song> songs = spotifyWrapper.getUserLikedSongs();
        userCatalog.setSpotifySongs(songs);
    }

    public static void addUser(String email,String password) {
        userRepository.addUser(email,password);
    }
//

//
    public static List<Song> getAllSongs(){
        return songRepository.getAllSongs();
    }
//
    public static List<Song> getSongs(){
        userCatalog.setSongs(songRepository.getSongs(userCatalog.getUserId()));
        return  userCatalog.getSongs();
    }
//
    public static List<Album> getAlbums(){
        userCatalog.setAlbums( albumRepository.getAlbums(userCatalog.getUserId()));
        return userCatalog.getAlbums();
    }
    public static void addSongDb(String title,String artist,String category){
        songRepository.addSongDB(title,artist,category);
    }
    public static Song getSong(String title){
        return songRepository.getSong(title);
    }

    public static List<Song> searchSong(String keyword){
        try {
            return songRepository.searchSongs(keyword);
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }
//
    public static List<Album> searchAlbum(String keyword){
        try {
            return albumRepository.searchAlbums(keyword);
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    public static Album getAlbum(String title){
        return albumRepository.getAlbum(title);
    }
//
    public static List<Playlist> getPlaylists(){
        userCatalog.setPlaylists(playlistRepository.getPlaylists(userCatalog.getUserId()));
        return userCatalog.getPlaylists();

    }
    public static List<Song> getSongsInPlaylist(int idPlaylist){
        List<Song> songsInPlaylist;
        for (Playlist playlist : userCatalog.getPlaylists()){
            if(playlist.getId()==idPlaylist){
                songsInPlaylist = playlist.getSongsInPlaylist();
                return songsInPlaylist;
            }
        }
        return new ArrayList<>();
    }

//
public static void addToCatalog(int idSong) {
    try {
        songRepository.addSongToUser(idSong, userCatalog.getUserId());
        System.out.println("Song added to catalog successfully.");
    } catch (RuntimeException e) {
        Throwable cause = e.getCause();
        if (cause instanceof SQLException) {
            handleCatalogSQLException((SQLException) cause);
        } else {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}

    public static void addToPlaylist(int idSong, int idPlaylist) {
        try {
            songRepository.addSongToPlaylist(idSong, idPlaylist);
            System.out.println("Song added to playlist successfully.");
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                handlePlaylistSQLException((SQLException) cause);
            } else {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private static void handleCatalogSQLException(SQLException e) {
        int errorCode = e.getErrorCode();

        if (errorCode == 1062) { // MySQL duplicate entry
            System.out.println("Song already added to catalog.");
        } else {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void handlePlaylistSQLException(SQLException e) {
        int errorCode = e.getErrorCode();

        if (errorCode == 1062) { // MySQL duplicate entry
            System.out.println("Song already added to playlist.");
        } else {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    public static Map<Song,Integer> startGame(List<Song> songs){
        Match match =new Match(songs);
        return  match.startGame();
    }
    public static void createPlaylist(String name) {
        playlistRepository.addPlaylist(userCatalog.getUserId(),name);
    }
    public static void deletePlaylist(int idPlaylist) {
        try{
            playlistRepository.deletePlaylist(idPlaylist);
            System.out.println("Playlist deleted successfully.");
        }
        catch (RuntimeException e){
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

}



