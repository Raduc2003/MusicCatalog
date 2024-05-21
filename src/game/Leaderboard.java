package game;

import Models.Song;

import java.util.Map;

public class Leaderboard {

    private int userId;

    private final Map<Song, Integer> top;

    private final Song winner ;

    public Leaderboard(Map<Song, Integer> map,int userId) {
        this.top = map;
        Map.Entry<Song, Integer> lastEntry = null;
        if (!map.isEmpty()) {
            lastEntry = map.entrySet().stream().reduce((first, second) -> second).orElse(null);
        }
        assert lastEntry != null;
        this.winner = lastEntry.getKey();
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Song, Integer> getTop() {
        return top;
    }

    public Song getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        return "Leaderboard{" + "\n"+
                "userId=" + userId +"\n"+
                ", top=" + top + "\n"+
                ", winner=" + winner +
                '}';
    }
}
