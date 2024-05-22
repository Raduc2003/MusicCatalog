package DB;
import Models.Song;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.yaml.snakeyaml.Yaml;


import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class SpotifyWrapper {
    private String clientId;
    private String clientSecret;
    private String accessToken;
    private static SpotifyWrapper spotifyWrapperInstc = null;
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private static String authorizationCode;

    private SpotifyWrapper() {
        Yaml yaml = new Yaml();
        try (InputStream in = JDBC.class.getClassLoader().getResourceAsStream("config.yml")) {
            if (in != null) {
                Map<String, Object> config = yaml.load(in);
                Map<String, String> dbConfig = (Map<String, String>) config.get("spotify");
                clientId = dbConfig.get("client_id");
                clientSecret = dbConfig.get("client_secret");
            } else {
                throw new RuntimeException("Failed to load config.yml");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    public static SpotifyWrapper getInstance() {
        if (spotifyWrapperInstc == null) {
            spotifyWrapperInstc = new SpotifyWrapper();
        }
        return spotifyWrapperInstc;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void authenticate() throws IOException {
        startHttpServer();

        // Step 1: Redirect user to Spotify's authorization page
        System.out.println("Please visit the following URL to authorize the application:");
        System.out.println("https://accounts.spotify.com/authorize?client_id=" + clientId + "&response_type=code&redirect_uri=" + REDIRECT_URI + "&scope=user-library-read");

        // Wait until the authorization code is received
        while (authorizationCode == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Step 3: Exchange authorization code for access token
        String authString = clientId + ":" + clientSecret;
        String authEncoded = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://accounts.spotify.com/api/token");
        httpPost.setHeader("Authorization", "Basic " + authEncoded);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        StringEntity entity = new StringEntity("grant_type=authorization_code&code=" + authorizationCode + "&redirect_uri=" + REDIRECT_URI);
        httpPost.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            String json = EntityUtils.toString(response.getEntity());
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            this.accessToken = jsonObject.get("access_token").getAsString();
        } finally {
            httpClient.close();
        }
    }

    private void startHttpServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
        server.createContext("/callback", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String query = exchange.getRequestURI().getQuery();
                String[] params = query.split("&");
                for (String param : params) {
                    String[] pair = param.split("=");
                    if (pair[0].equals("code")) {
                        authorizationCode = pair[1];
                        String response = "Authorization successful! You can close this window.";
                        exchange.sendResponseHeaders(200, response.length());
                        exchange.getResponseBody().write(response.getBytes());
                        exchange.getResponseBody().close();
                    }
                }
            }
        });
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.start();
    }

    public List<Song> getUserLikedSongs() throws IOException {
        if (accessToken == null) {
            authenticate();
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://api.spotify.com/v1/me/tracks");
        httpGet.setHeader("Authorization", "Bearer " + accessToken);

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String json = EntityUtils.toString(response.getEntity());
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonArray items = jsonObject.getAsJsonArray("items");

            List<Song> likedSongs = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                JsonObject item = items.get(i).getAsJsonObject();
                JsonObject track = item.getAsJsonObject("track");
                int id = track.get("id").getAsString().hashCode(); // Using hashCode as a simple unique ID
                String title = track.get("name").getAsString();
                String artist = track.getAsJsonArray("artists").get(0).getAsJsonObject().get("name").getAsString();
                String category = track.getAsJsonObject("album").get("name").getAsString(); // Using album name as category
                likedSongs.add(new Song(id, title, artist, category));
            }

            return likedSongs;
        } finally {
            httpClient.close();
        }
    }

    public static void main(String[] args) {
        try {
            SpotifyWrapper spotifyWrapper = SpotifyWrapper.getInstance();
            List<Song> likedSongs = spotifyWrapper.getUserLikedSongs();
            for (Song song : likedSongs) {
                System.out.println(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}