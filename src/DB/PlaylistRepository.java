package DB;

import Models.Playlist;
import Models.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistRepository extends JDBC  {

    private static PlaylistRepository playlistRepository= null;
    private PlaylistRepository(){}
    public static PlaylistRepository getInstance(){
        if(playlistRepository==null){
            playlistRepository = new PlaylistRepository();
        }
        return playlistRepository;
    }
    private static final String query = "SELECT * FROM Playlist WHERE idUser= ?;";

    public ArrayList<Song> getSongsInPlaylist(String titlePlaylist){

        String query = "SELECT s.id, s.title, s.artist, s.category FROM Song s JOIN Song_in_playlist sia ON s.id = sia.idSong JOIN Playlist a ON sia.idPlaylist = a.id WHERE a.title = ?;";
        ArrayList<Song> songs = new ArrayList<>();
        try(Connection connection =getConnection();
            PreparedStatement statement = connection.prepareStatement(query);){
            statement.setString(1,titlePlaylist);
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


    public  ArrayList<Playlist> getPlaylists(int idUser) {

        ArrayList<Playlist> Playlists = new ArrayList<>();

        try(Connection connection =getConnection();
            PreparedStatement statement = connection.prepareStatement(query);){
            statement.setInt(1,idUser);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){

                int id = rs.getInt("id");
                String title = rs.getString("name");

                ArrayList<Song> songs = getSongsInPlaylist(title);
               Playlists.add(new Playlist(id,idUser,title,songs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Playlists;
    }
    public  Playlist getPlaylist(String name){
        String query ="Select * from Playlist where name=?;";
        ArrayList<Song> songs = getSongsInPlaylist(name);
        Playlist Playlist = null;
        try(Connection conn = getConnection();
            PreparedStatement statement =conn.prepareStatement(query)){
            statement.setString(1,name);
            try(ResultSet rs = statement.executeQuery()){
                if (rs.next()){
                    Playlist = new Playlist(rs.getInt("id"),rs.getInt("idUser"),rs.getString("name"),songs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Playlist;

    }
}
