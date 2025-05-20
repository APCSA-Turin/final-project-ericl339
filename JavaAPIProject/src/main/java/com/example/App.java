package com.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class App {
    public static void main(String[] args) {

        try {
            String bearerCode = twitchGetter.getBearer(); // access code
            String clientId = twitchGetter.getClientId(); // client id
            String clientSecret = twitchGetter.getClientSecret(); // client secret

            // String output = API.getData("https://api.igdb.com/v4/genres/", bearerCode, clientId, "fields *; limit 500;");
            // // i learned this from https://www.tutorialspoint.com/how-to-write-create-a-json-array-using-java
            // JSONArray genreArray = new JSONArray(output);

            // ArrayList<GenreInfo> genreList = new ArrayList<>();
            // for (int i = 0; i < genreArray.length(); i++) {
            //     JSONObject genre = genreArray.getJSONObject(i);
            //     GenreInfo item = new GenreInfo(genre.getString("name"), genre.getInt("id"));
            //     genreList.add(item);
            //     // System.out.println("count: " + (i+1));
            //     // System.out.println("id: " + genre.getInt("id"));
            //     // System.out.println("genre: " + genre.getString("name"));
            // }



            String output = API.getData("https://api.igdb.com/v4/games/count", bearerCode, clientId, "fields id; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 1;");
            JSONObject gameCountObject = new JSONObject(output);
            int gameCount = gameCountObject.getInt("count");
            int offset = (int) (Math.random() * ((gameCount - 10) - 0 + 1) + 0);
            output = API.getData("https://api.igdb.com/v4/games", bearerCode, clientId, "fields name, genres.*, game_engines.*, involved_companies.*, platforms.*, game_type, summary; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 10; offset " + offset + ";");
            // i learned this from https://www.tutorialspoint.com/how-to-write-create-a-json-array-using-java
            JSONArray gameArray = new JSONArray(output);

            Scanner scan = new Scanner(System.in);
            System.out.println("Welcome! Press x to start");
            String input = scan.nextLine();
            while (input.equals("x")) {
                ArrayList<GameInfo> gameList = new ArrayList<>();
                for (int i = 0; i < gameArray.length(); i ++) {
                    JSONObject game = gameArray.getJSONObject(i);

                    ArrayList<String> genreName = new ArrayList<>();
                    int categoryId = game.getInt("game_type");
                    ArrayList<String> gameEngineName = new ArrayList<>();
                    ArrayList<String> companyName = new ArrayList<>();
                    ArrayList<String> platformName = new ArrayList<>();
                    String name = game.getString("name");
                    String summary = game.getString("summary");
                    String output1 = API.getData("https://api.igdb.com/v4/game_types", bearerCode, clientId, "fields *; where id = (" + categoryId + ");");
                    JSONArray gameTypeArray = new JSONArray(output1);
                    JSONObject typeInfo = gameTypeArray.getJSONObject(0);
                    String gameType = typeInfo.getString("type");

                    JSONArray genres = game.getJSONArray("genres");
                    for (int j = 0; j < genres.length(); j ++) {
                        JSONObject k = genres.getJSONObject(j);
                        genreName.add(k.getString("name"));
                    }

                    JSONArray engines = game.getJSONArray("game_engines");
                    for (int j = 0; j < engines.length(); j ++) {
                        JSONObject k = engines.getJSONObject(j);
                        gameEngineName.add(k.getString("name"));
                    }

                    JSONArray companies = game.getJSONArray("involved_companies");
                    for (int j = 0; j < companies.length(); j ++) {
                        JSONObject k = companies.getJSONObject(j);
                        int companyId = k.getInt("company");
                        String output2 = API.getData("https://api.igdb.com/v4/companies", bearerCode, clientId, "fields name; where id = (" + companyId + ");");
                        JSONArray outputCompany = new JSONArray(output2);
                        JSONObject y = outputCompany.getJSONObject(0);
                        companyName.add(y.getString("name"));
                    }

                    JSONArray platforms = game.getJSONArray("platforms");
                    for (int j = 0; j < platforms.length(); j ++) {
                        JSONObject k = platforms.getJSONObject(j);
                        platformName.add(k.getString("name"));
                    }

                    GameInfo item = new GameInfo(genreName,gameType,gameEngineName,companyName,platformName,name,summary);
                    gameList.add(item);
                }

                for (int i = 0; i < gameList.size(); i ++) {
                    System.out.println(i + 1);
                    System.out.println("Genre: " + gameList.get(i).getGenreName());
                    System.out.println("Type: " + gameList.get(i).getGameType());
                    System.out.println("Engine: " + gameList.get(i).getGameEngineName());
                    System.out.println("Company Name: " + gameList.get(i).getCompanyName());
                    System.out.println("Platform name: " + gameList.get(i).getplatformName());
                    System.out.println("Name: " + gameList.get(i).getName());
                    System.out.println("Summary: " + gameList.get(i).getSummary());
                    System.out.println();
                }
                System.out.println("Press x to continue or press n to exit");
                input = scan.nextLine();
                if (input.equals("n")) {
                    break;
                }
                //i learned this from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
                System.out.print("Loading");
                TimeUnit.SECONDS.sleep(1);
                System.out.print(".");
                TimeUnit.SECONDS.sleep(1);
                System.out.print(".");
                TimeUnit.SECONDS.sleep(1);
                output = API.getData("https://api.igdb.com/v4/games/count", bearerCode, clientId, "fields id; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 1;");
                gameCountObject = new JSONObject(output);
                gameCount = gameCountObject.getInt("count");
                offset = (int) (Math.random() * ((gameCount - 10) - 0 + 1) + 0);
                output = API.getData("https://api.igdb.com/v4/games", bearerCode, clientId, "fields name, genres.*, game_engines.*, involved_companies.*, platforms.*, game_type, summary; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 10; offset " + offset + ";");
                gameArray = new JSONArray(output);
                System.out.print(".");
                TimeUnit.SECONDS.sleep(1);
            }

        } catch (Exception e) {
            System.err.println("Failed to get token: " + e.getMessage());
            e.printStackTrace();
        }
    }
}