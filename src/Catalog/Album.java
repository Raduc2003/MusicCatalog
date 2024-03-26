package Catalog;

import java.util.ArrayList;

public class Album extends Song{
    public ArrayList<Song> songs;

    public Album(int id, String title, String artist, String category,ArrayList<Song> songs) {
        super(id, title, artist, category);
        this.songs =songs;
    }

}
