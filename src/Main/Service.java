package Main;

import DB.*;
import Models.*;
import game.Match;

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
    public static void addUser(String email,String password) {
        userRepository.addUser(email,password);
    }
//
    public static Song searchSong(String title){
        return songRepository.getSong(title);
    }
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
//
    public static Album getAlbum(String title){
        return albumRepository.getAlbum(title);
    }
//
    public static List<Playlist> getPlaylists(){
        userCatalog.setPlaylists(playlistRepository.getPlaylists(userCatalog.getUserId()));
        return userCatalog.getPlaylists();

    }
//
    public static void addToPlaylist(int idSong,int idPlayist){
        songRepository.addSongToPlaylist(idSong,idPlayist);
    }
//
    public static void addToCatalog(int idSong){
        songRepository.addSongToUser(idSong, userCatalog.getUserId());
    }
//
    public static Map<Song,Integer> startGame(){
        List<Song> songs =  userCatalog.getSongs();
        Match match =new Match(songs);
        return  match.startGame();
    }
//



}



