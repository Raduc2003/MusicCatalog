package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Playlist {
//    Problema cu playlsit gol
  protected int id;
    protected int idUser;
    protected String name;
    protected List<Song> songsInPlaylist;

    public Playlist(int id, int idUser, String name, List<Song> songsInPlaylist) {
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

    public List<Song> getSongsInPlaylist() {
        return songsInPlaylist;
    }

    public void setSongsInPlaylist(List<Song> songsInPlaylist) {
        this.songsInPlaylist = songsInPlaylist;
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
    @Override
    public String toString() {
        if (songsInPlaylist.isEmpty()) return "Playlist gol";
        return "Playlist{" +
                "name='" + name + '\'' +
                ", songsInPlaylist=" + songsInPlaylist +
                '}';
    }


}
