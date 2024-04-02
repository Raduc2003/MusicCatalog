import DB.AlbumRepository;
import DB.PlaylistRepository;
import DB.SongRepository;
import DB.UserRepository;
import Models.*;

import java.util.ArrayList;
import java.util.List;

public  class Service {

    //storage
    private static User user =null ;

    private static final Catalog userCatalog = new Catalog();

    //repositories
    private static SongRepository songRepository = SongRepository.getInstance();

    private static AlbumRepository albumRepository = AlbumRepository.getInstance();

    private static UserRepository userRepository = UserRepository.getInstance();

    private static PlaylistRepository playlistRepository = PlaylistRepository.getInstance();
    //getter setter


    public static User getUser(String email,String password) {
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

    public static Song getSong(String title){
        return songRepository.getSong(title);
    }

    public static Album getAlbum(String title){
        return albumRepository.getAlbum(title);
    }

    public static void initializeCatalog() {
        ArrayList<Song> songs = new ArrayList<>();
    }
}



