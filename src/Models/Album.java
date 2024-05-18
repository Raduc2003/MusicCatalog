package Models;

import java.util.ArrayList;
import java.util.List;

public class Album extends Song{
    public List<Song> songs;

    public Album(int id, String title, String artist, String category,List<Song> songs) {
        super(id, title, artist, category);
        if( songs == null){
            this.songs = new ArrayList<>();
        }
        else {
            this.songs =songs;

        }
    }

    @Override
    public String toString() {
        return "Album{" +
                "songs=" + songs +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
