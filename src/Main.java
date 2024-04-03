import Models.*;
import game.Leaderboard;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);

    private  static ArrayList<Leaderboard> leaderboards = new ArrayList<>();
    public static void main(String[] args) {
        int option;

        do {

            if (Service.getUser()==null){
                System.out.println("Currently not logged in");
            }
            else{
                System.out.println("Logged in as: " + Service.getUser().getEmail());
            }
            System.out.println("### Music App Menu ###");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Match-up Between Songs");
            System.out.println("4. View Songs");
            System.out.println("5. View Albums");
            System.out.println("6. Search Song");
            System.out.println("7. Search Album");
            System.out.println("8. View Playlists");
            System.out.println("9. Add New Song to DB");
            System.out.println("10. Add Song to Playlist");
            System.out.println("11. Add Song to Catalog");
            System.out.println("12. View Leaderboards");
            System.out.println("13. Exit");
            System.out.print("Select an option: ");
            option = Integer.parseInt(in.nextLine());
            switch (option) {
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
                    viewSongs();
                    clearConsole();
                    break;
                case 5:
                    viewAlbums();
                    clearConsole();
                    break;
                case 6:
                    searchSong();
                    clearConsole();
                    break;
                case 7:
                    searchAlbum();
                    clearConsole();
                    break;
                case 8:
                    viewPlaylists();
                    clearConsole();
                    break;
                case 9:
                    addNewSongToDB();
                    clearConsole();
                    break;
                case 10:
                    addSongToPlaylist();
                    clearConsole();
                    break;
                case 11:
                    addSongToCatalog();
                    clearConsole();
                    break;
                case 12:
                    clearConsole();
                    viewLeaderboars();

                    break;
                case 13:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        } while (option != 13);
    }

    private static void viewLeaderboars() {
        for (Leaderboard lead : leaderboards){
            System.out.println(lead.toString());
        }
    }

    private static void addSongToCatalog() {
        //to be implemented a partial Search feature
        System.out.println("Search up a Song to add to catalog:");
        String songName = in.nextLine();
        Song song  =Service.getSong(songName);
        if(song==null){
            System.out.println("Song doesn't exist");
        }
        else{
            int idSong = song.id;
            Service.addToCatalog(idSong,Service.getUser().getId());
        }

    }

    private static void addSongToPlaylist() {
        //to be implemented a partial Search feature
        System.out.println("Select the desired playlist");
        ArrayList<Playlist> playlists = Service.getPlaylists(Service.getUser().getId());
        int i=1;
        for(Playlist playlist : playlists){
            System.out.println(i+"."+playlist.getName());
        }
        System.out.println("choice:");
        int choice = in.nextInt();
        int idPLaylist = playlists.get(choice-1).getId();
        System.out.println("Search up a Song to add to playlist:");
        String songName = in.nextLine();
        Song song  =Service.getSong(songName);
        if(song==null){
            System.out.println("Song doesn't exist");
        }
        else{
            int idSong = song.id;
            Service.addToPlaylist(idSong,idPLaylist);
        }

    }

    private static void addNewSongToDB() {

    }

    private static void viewPlaylists() {
    }

    private static void searchAlbum() {
    }

    private static void searchSong() {
    }

    private static void viewAlbums() {
    }

    private static void matchUpBetweenSongs() {
        int userId = Service.getUser().getId();
        Map <Song,Integer> top =Service.startGame(userId);
        leaderboards.add(new Leaderboard(top,userId));
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

    private static void viewSongs() {
        ArrayList<Song> songs = Service.getAllSongs();
        if (songs.isEmpty()) {
            System.out.println("No songs available.");
        } else {
            for (Song song : songs) {
                System.out.println(song.title + " by " + song.artist);
            }
        }
    }
    public static void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }


}
