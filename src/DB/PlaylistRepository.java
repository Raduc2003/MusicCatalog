package DB;

import Models.Playlist;
import Models.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Song> getSongsInPlaylist(String titlePlaylist){

        String query = "SELECT s.id, s.title, s.artist, s.category FROM Song s JOIN Song_in_playlist sia ON s.id = sia.idSong JOIN Playlist a ON sia.idPlaylist = a.id WHERE a.name = ?;";
        List<Song> songs = new ArrayList<>();
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


    public  List<Playlist> getPlaylists(int idUser) {

        List<Playlist> Playlists = new ArrayList<>();

        try(Connection connection =getConnection();
            PreparedStatement statement = connection.prepareStatement(query);){
            statement.setInt(1,idUser);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){

                int id = rs.getInt("id");
                String title = rs.getString("name");

                List<Song> songs = getSongsInPlaylist(title);
               Playlists.add(new Playlist(id,idUser,title,songs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Playlists;
    }

    public  String addPlaylist(int idUser,String title){
        String query ="INSERT INTO Playlist (idUser,name) values(?,?)";
        String status;
        try(Connection connection =getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1,idUser);
            statement.setString(2,title);
            statement.executeUpdate();
            status = "Playlist added";
            return status;
        }catch (SQLException e){

            throw  new RuntimeException();
        }

    }
    public  String changeNamePlaylist(int idPlaylist,String title){
        String query ="UPDATE Playlist set name = ? where id =?";
        String status;
        try(Connection connection =getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,title);
            statement.setInt(2,idPlaylist);
            statement.executeUpdate();
            status = "Playlist name changed ";
            return status;
        }catch (SQLException e){

            throw  new RuntimeException();
        }

    }
    public  Playlist getPlaylist(String name){
        String query ="Select * from Playlist where name=?;";
        List<Song> songs = getSongsInPlaylist(name);
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

    public void deletePlaylist(int idPlaylist) {
        String query = "DELETE FROM Playlist WHERE id = ?;";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idPlaylist);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
