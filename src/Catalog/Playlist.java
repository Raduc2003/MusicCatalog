package Catalog;

import java.util.ArrayList;
import java.util.Arrays;

public class Playlist {
//    Problema cu playlsit gol
    protected int userId;
    protected String name;
    protected ArrayList<Song> songsInPlaylist;

    public Playlist(int userId,String name,  ArrayList<Song> songsInPlaylist) {
        this.name = name;
        this.songsInPlaylist = songsInPlaylist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public ArrayList<Song> getSongsInPlaylist() {
        return songsInPlaylist;
    }

    public void setSongsInPlaylist(ArrayList<Song> songsInPlaylist) {
        this.songsInPlaylist = songsInPlaylist;
    }

    @Override
    public String toString() {
        if (songsInPlaylist.isEmpty()) return "Playlist gol";
        return "Playlist{" +
                "name='" + name + '\'' +
                ", songsInPlaylist=" + songsInPlaylist +
                '}';
    }

    public void addSong(Song song){
        songsInPlaylist.add(song);
    }
    public void deleteSong(String name){
        for (Song song : songsInPlaylist) {
            if (song.title==name){
                songsInPlaylist.remove(song);
            }
        }

    }
    public int getLength(){
        return songsInPlaylist.size();
    }
    public static void main(String[] args) {
        Song song = new Song(1,"Billie Jean","Michael Jackson","Pop");
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(song);
        Playlist playlist= new Playlist(1,"Pop Music boom",songs);

        System.out.println(playlist);
        playlist.addSong(song);
//        System.out.printf(playlist.toString());
        playlist.deleteSong("Billie Jean");
//        playlist.deleteSong("Billie Jean");
        System.out.println(playlist);

    }
}
