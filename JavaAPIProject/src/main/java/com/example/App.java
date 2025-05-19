package com.example;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class App {
    public static void main(String[] args) {
        String bearerCode = null;
        String output = null;
        try {
            bearerCode = twitchGetter.getBearer();
            String clientId = twitchGetter.getClientId();
            String clientSecret = twitchGetter.getClientSecret();
            System.out.println(clientId);
            System.out.println(clientSecret);
            System.out.println(bearerCode);  

            output = API.getData("https://api.igdb.com/v4/genres/", bearerCode, clientId, "fields *; limit 2;");

            // i learned this from https://www.tutorialspoint.com/how-to-write-create-a-json-array-using-java
            JSONArray genreArray = new JSONArray(output);

            ArrayList<GenreInfo> genreList = new ArrayList<>();

            for (int i = 0; i < genreArray.length(); i++) {

                JSONObject genre = genreArray.getJSONObject(i);
                GenreInfo item = new GenreInfo(genre.getString("name"), genre.getInt("id"));
                genreList.add(item);

                System.out.println("count: " + (i+1));
                System.out.println("id: " + genre.getInt("id"));
                System.out.println("genre: " + genre.getString("name"));
            }

            output = API.getData("https://api.igdb.com/v4/games", bearerCode, clientId, "fields *; limit 500;");
            JSONArray gameArray = new JSONArray(output);
            ArrayList<GameInfo> gameList = new ArrayList<>();
            

        } catch (Exception e) {
            System.err.println("Failed to get token: " + e.getMessage());
            e.printStackTrace();
        }
        // String clientId = twitchGetter.getClientId();
        // String clientSecret = twitchGetter.getClientSecret();
        // System.out.println(clientId);
        // System.out.println(clientSecret);
        // System.out.println(bearerCode);        
        // System.out.println();
        // System.out.println(output);
        }
}
