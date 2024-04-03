package Models;

import java.util.ArrayList;
import java.util.Objects;

public class Playlist {
//    Problema cu playlsit gol
  protected int id;
    protected int idUser;
    protected String name;
    protected ArrayList<Song> songsInPlaylist;

    public Playlist(int id, int idUser, String name, ArrayList<Song> songsInPlaylist) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.songsInPlaylist = songsInPlaylist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
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
        songsInPlaylist.removeIf(song -> Objects.equals(song.title, name));

    }
    public int getLength(){
        return songsInPlaylist.size();
    }
//    public static void main(String[] args) {
//        Song song = new Song(1,"Billie Jean","Michael Jackson","Pop");
//        ArrayList<Song> songs = new ArrayList<>();
//        songs.add(song);
//        Playlist playlist= new Playlist(1,"Pop Music boom",songs);
//
//        System.out.println(playlist);
//        playlist.addSong(song);
////        System.out.printf(playlist.toString());
//        playlist.deleteSong("Billie Jean");
////        playlist.deleteSong("Billie Jean");
//        System.out.println(playlist);
//
//    }
}
