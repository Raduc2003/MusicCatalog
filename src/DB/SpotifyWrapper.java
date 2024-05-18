package DB;

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


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.Executors;

public class SpotifyWrapper {
    private String clientId;
    private String clientSecret;
    private String accessToken;
    private static SpotifyWrapper spotifyWrapperInstc = null;
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private static String authorizationCode;

    private SpotifyWrapper() {
        clientId = "11b84b74a00b41c69846a9dd74380ee5";
        clientSecret = "18a65d7cdc6046a989b3d580af84105b";
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

    private String getAccessToken() {
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

    public JsonArray getUserLikedSongs() throws IOException {
        if (accessToken == null) {
            authenticate();
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://api.spotify.com/v1/me/tracks");
        httpGet.setHeader("Authorization", "Bearer " + accessToken);

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String json = EntityUtils.toString(response.getEntity());
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            return jsonObject.getAsJsonArray("items");
        } finally {
            httpClient.close();
        }
    }

    public static void main(String[] args) {
        try {
            SpotifyWrapper spotifyWrapper = SpotifyWrapper.getInstance();
            JsonArray likedSongs = spotifyWrapper.getUserLikedSongs();
            System.out.println(likedSongs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
