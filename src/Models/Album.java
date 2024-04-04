package Models;

import java.util.ArrayList;

public class Album extends Song{
    public ArrayList<Song> songs;

    public Album(int id, String title, String artist, String category,ArrayList<Song> songs) {
        super(id, title, artist, category);
        if( songs == null){
            this.songs = new ArrayList<>();
        }
        else {
            this.songs =songs;

        }
    }



}
