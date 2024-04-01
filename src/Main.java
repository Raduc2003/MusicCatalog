import Models.Song;
import Models.User;
import game.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int option = 0;

        do {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Play Game");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            option = in.nextInt();
            in.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    System.out.println("Register");
                    System.out.println("Email:");
                    String email = in.nextLine();
                    System.out.println("Password:");
                    String password = in.nextLine();

                    Service.addUser(email, password);
                    break;
                case 2:
                    System.out.println("Login");
                    System.out.println("Email:");
                    String email1 = in.nextLine();
                    System.out.println("Password:");
                    String password1 = in.nextLine();
                    User currentUser = Service.getUser(email1, password1);
                    System.out.println(currentUser);
                    break;
                case 3:
                    // Presupunem că avem o metodă pentru a începe jocul
                    //List<Song> songs = Service.getSongs();
                    //Match meci = new Match(songs);
                    //System.out.println(meci.startGame().toString());
                    System.out.println("Game start logic here.");
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        } while (option != 4);

        in.close();
    }
}
