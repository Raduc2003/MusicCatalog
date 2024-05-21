package DB;

import Models.Album;
import Models.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepository extends JDBC  {

    private static AlbumRepository albumRepository= null;
    private AlbumRepository(){}
    public static AlbumRepository getInstance(){
        if(albumRepository==null){
            albumRepository = new AlbumRepository();
        }
        return albumRepository;
    }
    private static final String query = "SELECT DISTINCT a.id, a.title, a.artist, a.category FROM Album a JOIN Song_in_album sia ON a.id = sia.idAlbum JOIN Song_in_playlist sip ON sia.idSong = sip.idSong JOIN Playlist p ON sip.idPlaylist = p.id WHERE p.idUser = ?;";

    public List<Song> getSongsInAlbum(String titleAlbum){

        String query = "SELECT s.id, s.title, s.artist, s.category FROM Song s JOIN Song_in_album sia ON s.id = sia.idSong JOIN Album a ON sia.idAlbum = a.id WHERE a.title = ?;";
        List<Song> songs = new ArrayList<>();
        try(Connection connection =getConnection();
            PreparedStatement statement = connection.prepareStatement(query);){
            statement.setString(1,titleAlbum);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                String category = rs.getString("category");
                songs.add(new Song(id,title,artist,category));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return songs;
    }

    public  List<Album> getAlbums(int idUser) {

        ArrayList<Album> albums = new ArrayList<>();

        try(Connection connection =getConnection();
            PreparedStatement statement = connection.prepareStatement(query);){
            statement.setInt(1,idUser);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){

                int id = rs.getInt("id");
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                String category = rs.getString("category");
                List<Song> songs = getSongsInAlbum(title);
               albums.add(new Album(id,title,artist,category,songs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return albums;
    }
    public  Album getAlbum(String name){
        String query ="Select * from Album where title=?;";
        List<Song> songs = getSongsInAlbum(name);
        Album album = null;
        try(Connection conn = getConnection();
            PreparedStatement statement =conn.prepareStatement(query)){
            statement.setString(1,name);
            try(ResultSet rs = statement.executeQuery()){
                if (rs.next()){
                    album = new Album(rs.getInt("id"),rs.getString("title"), rs.getString("artist"), rs.getString("category"),songs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return album;

    }

    public List<Album> searchAlbums(String keyword) {
        String query = "SELECT * FROM Album WHERE title LIKE ?;";
        List<Album> albums = new ArrayList<>();
        try(Connection connection =getConnection();
            PreparedStatement statement = connection.prepareStatement(query);){
            statement.setString(1,"%"+keyword+"%");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String artist = rs.getString("artist");
                String category = rs.getString("category");
                List<Song> songs = getSongsInAlbum(title);
                albums.add(new Album(id,title,artist,category,songs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return albums;
    }
}
