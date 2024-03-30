package Models;

import java.util.ArrayList;

public class Album extends Song{
    public ArrayList<Song> songs;
    public int length = songs.size();

    public Album(int id, String title, String artist, String category,ArrayList<Song> songs) {
        super(id, title, artist, category);
        this.songs =songs;
    }



}
