package DB;

import Models.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

//    public ArrayList<Song> addData() {
//
//    }
    public  ArrayList<Song> getData() {
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
}
