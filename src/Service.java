import DB.AlbumRepository;
import DB.PlaylistRepository;
import DB.SongRepository;
import DB.UserRepository;
import Models.*;
import game.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public  class Service {

    //storage
    private static User user =null ;
    private static final Catalog userCatalog = new Catalog();
    //repositories
    private static final SongRepository songRepository = SongRepository.getInstance();

    private static final AlbumRepository albumRepository = AlbumRepository.getInstance();

    private static final UserRepository userRepository = UserRepository.getInstance();

    private static final PlaylistRepository playlistRepository = PlaylistRepository.getInstance();
    //getter setter


    public static User getUser() {
        return user;
    }

    public static User logUser(String email, String password) {
        user = userRepository.getUser(email,password);
        return  user;
    }

    public static void addUser(String email,String password) {
        userRepository.addUser(email,password);
    }

    public static Song searchSong(String title){
        return songRepository.getSong(title);
    }

    public static ArrayList<Song> getAllSongs(){
        return  songRepository.getAllSongs();
    }

    public static ArrayList<Song> getSongs(int idUser){
        return songRepository.getSongs(idUser);
    }

    public static ArrayList<Album> getAlbums(int idUser){
        return albumRepository.getAlbums(idUser);
    }
    public static void addSongDb(String title,String artist,String category){
        songRepository.addSongDB(title,artist,category);
    }
    public static Song getSong(String title){
        return songRepository.getSong(title);
    }

    public static Album getAlbum(String title){
        return albumRepository.getAlbum(title);
    }

    public static ArrayList<Playlist> getPlaylists(int idUser){

        return  playlistRepository.getPlaylists(idUser);
    }

    public static void addToPlaylist(int idSong,int idPlayist){
        songRepository.addSongToPlaylist(idSong,idPlayist);
    }

    public static void addToCatalog(int idSong,int idUser){
        songRepository.addSongToUser(idSong,idUser);
    }

    public static Map<Song,Integer> startGame(int idUser){
        ArrayList<Song> songs =  getSongs(idUser);
        Match match =new Match(songs);
        return  match.startGame();
    }
    public static void initializeCatalog() {
        ArrayList<Song> songs = new ArrayList<>();
    }


//    public static void main(String[] args) {
//        ArrayList<Song> songs =new ArrayList<>();
//
//        songs = songRepository.getSongs(1);
//
//        System.out.println(songs);
//
//    }
}



