package game;

import Models.Song;

import java.util.*;

public class Match {

    private Dictionary<Song,Integer> topDict = new Hashtable<Song,Integer>();

    public Match(ArrayList<Song> songs){
        for ( Song song : songs){
            topDict.put(song,0);
        }
    }
    public  Song chooseBetween(Song a, Song b){
        Scanner in =new Scanner(System.in);
        System.out.println("Choose between:");
        System.out.println("1."+a+ "2."+b);
        int resp=0;
        Song response = null;
        while(resp==0){
            resp = in.nextInt();
            if (resp == 1){
                response = a;
            } else if (resp==2) {
                response =b;
            }
            else {
                System.out.println("Choose between 1 and 2");
                resp = 0;
            }
        }
        return  response;
    }

    public Song getRandSong() {
        ArrayList<Song> zeroValueSongs = new ArrayList<>();
        Enumeration<Song> keys = topDict.keys();
        while (keys.hasMoreElements()) {
            Song key = keys.nextElement();
            if (topDict.get(key).equals(0)) {
                zeroValueSongs.add(key);
            }
        }

        if (zeroValueSongs.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(zeroValueSongs.size());
        return zeroValueSongs.get(randomIndex);
    }
    public  ArrayList<Song> startGame(){
        Song a = getRandSong();
        Song b = getRandSong();
        Song winner = null;

        int exit =0;
        int topC =1;
        while(exit==0){
            System.out.println("Battle:");
            winner = chooseBetween(a,b);
            int val = topDict.get(winner) +topC;
            topC++;
            topDict.put(winner,val);
            a = winner;
            b= getRandSong();
            System.out.println("Again?(Y/N)");

            Scanner in = new Scanner(System.in);
            String r = in.nextLine();
            if (r == "y"){
                exit=1;
            } else if (r=="n") {
                exit=0;
            }
            else {
                System.out.println("Bad input");
            }

        }
        return 
    }
}
