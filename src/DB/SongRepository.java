package DB;

import Models.Album;
import Models.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongRepository extends JDBC  {

    private static SongRepository songRepository= null;

    private SongRepository(){}

    public static SongRepository getInstance(){
        if(songRepository==null){
            songRepository = new SongRepository();
        }
        return songRepository;
    }

    private static final String query = "SELECT * FROM Song;";

    public void addSongToUser(int idSong,int idUser){
        String query = "INSERT INTO Song_in_catalog (idSong, idUser) VALUES (?,?);";
        try (Connection connection = getConnection();
             // Try-with-resources statement for PreparedStatement ensures it's also closed
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idSong);
            statement.setInt(2, idUser);

            // Execute the update
            statement.executeUpdate(); // Use executeUpdate for INSERT, UPDATE, DELETE operations

        } catch (SQLException e) {
            // Consider a more specific handling or logging of the SQL exception here
            throw new RuntimeException("Failed to add song in catalog", e);
        }
    }
    public void addSongToPlaylist(int idSong,int idPlaylist){
        String query = "INSERT INTO Song_in_playlist (idSong, idPlaylist) VALUES (?,?);";
        try (Connection connection = getConnection();
             // Try-with-resources statement for PreparedStatement ensures it's also closed
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idSong);
            statement.setInt(2, idPlaylist);

            // Execute the update
            statement.executeUpdate(); // Use executeUpdate for INSERT, UPDATE, DELETE operations

        } catch (SQLException e) {
            // Consider a more specific handling or logging of the SQL exception here
            throw new RuntimeException("Failed to add song in Playlist", e);
        }
    }
    public void addSongDB(String title,String artist, String category) {
        String postQuery = "INSERT INTO Song (title,artist,category) VALUES(?,?,?);";
        try (Connection connection = getConnection();
             // Try-with-resources statement for PreparedStatement ensures it's also closed
             PreparedStatement statement = connection.prepareStatement(postQuery)) {
            statement.setString(1, title);
            statement.setString(2, artist);
            statement.setString(3, category);
            // Execute the update
            statement.executeUpdate(); // Use executeUpdate for INSERT, UPDATE, DELETE operations

        } catch (SQLException e) {
            // Consider a more specific handling or logging of the SQL exception here
            throw new RuntimeException("Failed to add Song", e);
        }
    }
    public  List<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        try(Connection connection =getConnection();
            PreparedStatement statement = connection.prepareStatement(query);){
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
    public  List<Song> getSongs(int idUser) {

        ArrayList<Song> songs = new ArrayList<>();
        String query ="SELECT s.id, s.title, s.artist, s.category FROM Song s JOIN Song_in_catalog sic ON s.id = sic.idSong WHERE sic.idUser = ?;";
        try(Connection connection =getConnection();
            PreparedStatement statement = connection.prepareStatement(query);){
            statement.setInt(1,idUser);
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
    public Song getSong(String name){
        String query ="Select * from Song where title=?;";

        Song song = null;
        try(Connection conn = getConnection();
            PreparedStatement statement =conn.prepareStatement(query)){
            statement.setString(1,name);
            try(ResultSet rs = statement.executeQuery()){
                if (rs.next()){
                    song = new Song(rs.getInt("id"),rs.getString("title"), rs.getString("artist"), rs.getString("category"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return song;

    }

    public List<Song> searchSongs(String keyword) {
        List<Song> songs = new ArrayList<>();

        String query = "SELECT * " +
                "FROM Song " +
                "WHERE title LIKE ? OR artist LIKE ? ";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);


            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Song song = new Song();
                    song.setId(rs.getInt("id"));
                    song.setTitle(rs.getString("title"));
                    song.setArtist(rs.getString("artist"));
                    songs.add(song);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return songs;
    }
}
