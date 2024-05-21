package Main;

import Models.*;
import Services.Service;
import game.Leaderboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {


    private static final Scanner in = new Scanner(System.in);
    private static final Catalog userCatalog =Catalog.getInstance();
    public static void main(String[] args) throws IOException {
        int option;

        do {
            System.out.println("### Music App Menu ###");
            if (userCatalog.getUser()==null){
                System.out.println("Currently not logged in");
                System.out.println("1. Create Account");
                System.out.println("2. Login");
            }
            else{
                System.out.println("Logged in as: " + userCatalog.getUser().getEmail());
                System.out.println("------------Spotify options-----------");
                if(userCatalog.getSpotifyToken()==null){
                    System.out.println("6. Login with Spotify");
                }
                else{
                    System.out.println("7. View Spotify Songs");
                    System.out.println("17. Match-up Between Songs");
                }

                System.out.println("------------User options-----------");
                System.out.println("3. Match-up Between Songs");
                System.out.println("4. View Songs");
                System.out.println("5. View Albums");
                System.out.println("8. View Playlists");
                System.out.println("15.Create Playlist");
                System.out.println("16.Delete Playlist");
                System.out.println("10. Add Song to Catalog");
                System.out.println("9. Add Song to Playlist");
                System.out.println("11. View Leaderboards");
                System.out.println("------------Admin DB options-----------");
                System.out.println("12.Add New Song to DB");
                System.out.println("13.View All songs in DB");

            }



            System.out.println("14. Exit");
            System.out.print("Select an option: ");
            option = Integer.parseInt(in.nextLine());
            switch (option) {
                case 6:
                    try {
                        Service.spotifyLogin();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    clearConsole();
                    break;
                case 7:
                    clearConsole();
                    getSpotifySongs();


                    break;
                case 17:
                    clearConsole();
                    matchUpBetweenSongsSpotify();
                    break;
                case 1:
                    createAccount();
                    clearConsole();
                    break;

                case 2:
                    login();
                    clearConsole();
                    break;
                case 3:
                     matchUpBetweenSongs();
                     clearConsole();
                    break;
                case 4:

                    clearConsole();

                    viewSongs();
                    break;
                case 5:
                    clearConsole();
                    viewAlbums();

                    break;

                case 8:
                    clearConsole();

                    viewPlaylists();
                    break;
                case 12:
                    addNewSongToDB();
                    clearConsole();
                    break;
                case 9:
                    clearConsole();
                    addSongToPlaylist();

                    break;
                case 10:
                    clearConsole();
                    addSongToCatalog();

                    break;
                case 11:
                    clearConsole();
                    viewLeaderboars();
                    break;
                case 13:
                    clearConsole();
                    viewAllSongs();
                case 15:
                    clearConsole();
                    createPlaylist();
                    break;
                case 14:
                    System.out.println("Exiting...");
                    break;
                case 16:
                    clearConsole();
                    deletePlaylist();
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        } while (option != 14);
    }

    private static void matchUpBetweenSongsSpotify() throws IOException {
        System.out.println("Match-up Between Songs");
        Service.getSpotifySongs();
        List<Song> songs = userCatalog.getSpotifySongs();
        Map <Song,Integer> top =Service.startGame(songs);
        List<Leaderboard> leaderboards = userCatalog.getLeaderboards();
        if (leaderboards == null) {
            leaderboards = new ArrayList<>();
        }
        leaderboards.add(new Leaderboard(top, userCatalog.getUserId()));
    }

    private static void viewLeaderboars() {
        for (Leaderboard lead : userCatalog.getLeaderboards()){
            System.out.println(lead.toString());
        }
    }
    private static void searchSong(){}

    private static void addSongToCatalog() {
        //add sql error handling for duplicate
        //to be implemented a partial Search feature
        System.out.println("Search up a Song to add to catalog:");
        String songName = in.nextLine();
        List<Song> songs = Service.searchSong(songName);
        if(songs==null){
            System.out.println("Song doesn't exist");
        }
        else{
            for  (int i=0;i< songs.size();i++){
                System.out.println(i+"."+ songs.get(i).getTitle()+" by " + songs.get(i).getArtist());
            }
            int op;
            System.out.println("Song id:");
            op= Integer.parseInt(in.nextLine());

            Service.addToCatalog(songs.get(op).getId());
        }

    }
    private static void createPlaylist() {
        System.out.println("Create Playlist");
        System.out.print("Playlist name: ");
        String name = in.nextLine();
        Service.createPlaylist(name);
    }
    private static void deletePlaylist() {
        System.out.println("Delete Playlist");
        List<Playlist> playlists = Service.getPlaylists();
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println(i + ". " + playlists.get(i).getName());
        }
        System.out.print("Select a playlist to delete: ");
        int index = Integer.parseInt(in.nextLine());
        Service.deletePlaylist(playlists.get(index).getId());
    }
    private static void getSpotifySongs() throws IOException {
        Service.getSpotifySongs();

        List<Song> songs = userCatalog.getSpotifySongs();
        for (Song song : songs) {
            System.out.println(song.getTitle() + " by " + song.getArtist());
        }
    }
    private static void addSongToPlaylist() {
        //to be implemented a partial Search feature
        //add sql error handling for duplicate
        System.out.println("Select the desired playlist");
        List<Playlist> playlists = Service.getPlaylists();
        int i=0;
        for(Playlist playlist : playlists){

            System.out.println(i+"." +playlist.getName());
            i++;
        }
        System.out.println("choice:");
        int choice = in.nextInt();
        in.nextLine();
        int idPLaylist = playlists.get(choice).getId();
        System.out.println("Search up a Song to add to catalog:");
        String songName = in.nextLine();
        List<Song> songs = Service.searchSong(songName);
        if(songs.isEmpty()){
            System.out.println("Didn't find any songs with that name");
        }
        else{
            for  (int j=0;j< songs.size();j++){
                System.out.println(j+"."+ songs.get(j).getTitle()+" by " + songs.get(j).getArtist());
            }
            int op;
            System.out.println("Song id:");
            op= Integer.parseInt(in.nextLine());

            Service.addToPlaylist(songs.get(op).getId(),idPLaylist);
        }

    }

    private static void addNewSongToDB() {
//        System.out.println("work in progress");
        System.out.println("Add new song to database");
        System.out.println("title:");
        String title = in.nextLine();
        System.out.println("artist:");
        String artist = in.nextLine();
        System.out.println("category:");
        String category = in.nextLine();
        Service.addSongDb(title,artist,category);
//        Song newSong = song
    }

    private static void viewPlaylists() {
//        System.out.println("work in progress");
        System.out.println("User playlists");
        List<Playlist> playlists = Service.getPlaylists();
        int i=0;
        for(Playlist playlist : playlists){
            System.out.println(i+"."+playlist.getName());
            i++;

        }

        System.out.println("select playlist:");
        int op = Integer.parseInt(in.nextLine());
        List<Song> songs = Service.getSongsInPlaylist(playlists.get(op).getId());
        for (Song song : songs){
            System.out.println(song.getTitle()+" by "+song.getArtist());
        }

    }


    private static void searchAlbum() {
        System.out.println("work in progress");
    }



    private static void viewAlbums() {
        System.out.println("User Albums");
        List<Album> albums = Service.getAlbums();
        int i=1;
        for(Album album : albums){
            System.out.println(i+"."+album.getTitle());
            i++;
        }
    }

    private static void matchUpBetweenSongs() {

        System.out.println("Match-up Between Songs");
        List<Song> songs = Service.getSongs();
        Map <Song,Integer> top =Service.startGame(songs);
        List<Leaderboard> leaderboards = userCatalog.getLeaderboards();
        if (leaderboards == null) {
            leaderboards = new ArrayList<>();
        }
        leaderboards.add(new Leaderboard(top, userCatalog.getUserId()));
    }

    private static void createAccount() {
        System.out.println("Register");
        System.out.print("Email: ");
        String email = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();
        Service.addUser(email, password);
    }

    private static void login() {
        System.out.println("Login");
        System.out.print("Email: ");
        String email = in.nextLine();
        System.out.print("Password: ");
        String password = in.nextLine();
        User currentUser = Service.logUser(email, password);
        System.out.println(currentUser);

    }
    private static void viewSongs(){
        System.out.println("------------Songs------------");
        List<Song> songs = Service.getSongs();
        if (songs.isEmpty()) {
            System.out.println("No songs available.");
        } else {
            for (Song song : songs) {
                System.out.println(song.getTitle() + " by " + song.getArtist());
            }
        }
        System.out.println("------------------------------");

    }

    private static void viewAllSongs() {
        System.out.println("------------Songs------------");
        List<Song> songs = Service.getAllSongs();
        if (songs.isEmpty()) {
            System.out.println("No songs available.");
        } else {
            for (Song song : songs) {
                System.out.println(song.getTitle() + " by " + song.getArtist());
            }
        }
        System.out.println("------------------------------");
    }
    public static void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }


}
