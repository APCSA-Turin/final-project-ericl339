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

            Scanner scan = new Scanner(System.in);
            System.out.println("Welcome! Press x to start");
            String input = scan.nextLine();
            System.out.println("Choose an option:\n1)7 random games to guess\n2)7 games to guess from a franchise");
            int option = scan.nextInt();
            scan.nextLine();
            String output = null;
            JSONObject gameCountObject = null;
            int gameCount;
            int offset;
            JSONArray gameArray = null;
            String franchiseChoice = null;
            int totalPoints = 0;
            int rounds = 0;
            if (option == 1) {
                System.out.print("Loading");
                output = API.getData("https://api.igdb.com/v4/games/count", bearerCode, clientId, "fields id; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 1;");
                TimeUnit.SECONDS.sleep(1);
                gameCountObject = new JSONObject(output);
                gameCount = gameCountObject.getInt("count");
                offset = (int) (Math.random() * ((gameCount - 7) - 0 + 1) + 0);
                output = API.getData("https://api.igdb.com/v4/games", bearerCode, clientId, "fields name, genres.*, game_engines.*, involved_companies.*, platforms.*, game_type, summary; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 7; offset " + offset + ";");
                // i learned this from https://www.tutorialspoint.com/how-to-write-create-a-json-array-using-java
                gameArray = new JSONArray(output);
            }
            else if (option == 2){

                System.out.println("Type in a popular franchise: ");
                franchiseChoice = scan.nextLine();
                System.out.print("Loading");
                output = API.getData("https://api.igdb.com/v4/games/count", bearerCode, clientId, "fields id; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 1; search \""+ franchiseChoice + "\";");
                TimeUnit.SECONDS.sleep(1);
                gameCountObject = new JSONObject(output);
                gameCount = gameCountObject.getInt("count");
                offset = (int) (Math.random() * ((gameCount - 7) - 0 + 1) + 0);
                output = API.getData("https://api.igdb.com/v4/games", bearerCode, clientId, "fields name, genres.*, game_engines.*, involved_companies.*, platforms.*, game_type, summary; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 7; offset " + offset + "; search \"" + franchiseChoice + "\";");
                // i learned this from https://www.tutorialspoint.com/how-to-write-create-a-json-array-using-java
                gameArray = new JSONArray(output);   
            }
            else{
                System.out.println("That's not an option!");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Defaulted to 7 random games");
                System.out.print("Loading");
                output = API.getData("https://api.igdb.com/v4/games/count", bearerCode, clientId, "fields id; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 1;");
                TimeUnit.SECONDS.sleep(1);
                gameCountObject = new JSONObject(output);
                gameCount = gameCountObject.getInt("count");
                offset = (int) (Math.random() * ((gameCount - 7) - 0 + 1) + 0);
                output = API.getData("https://api.igdb.com/v4/games", bearerCode, clientId, "fields name, genres.*, game_engines.*, involved_companies.*, platforms.*, game_type, summary; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 7; offset " + offset + ";");
                // i learned this from https://www.tutorialspoint.com/how-to-write-create-a-json-array-using-java
                gameArray = new JSONArray(output);
            }

            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(1);            
            while (input.equals("x")) {
                TimeUnit.SECONDS.sleep(1);
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

                // for (int i = 0; i < gameList.size(); i ++) {
                //     System.out.println(i + 1);
                //     System.out.println("Genre: " + gameList.get(i).getGenreName());
                //     System.out.println("Type: " + gameList.get(i).getGameType());
                //     System.out.println("Engine: " + gameList.get(i).getGameEngineName());
                //     System.out.println("Company Name: " + gameList.get(i).getCompanyName());
                //     System.out.println("Platform name: " + gameList.get(i).getplatformName());
                //     System.out.println("Name: " + gameList.get(i).getName());
                //     System.out.println("Summary: " + gameList.get(i).getSummary());
                //     System.out.println();
                // }
                int roundPoints = 7;
                int randomNumber = (int) (Math.random() * (gameList.size() - 1 + 0 + 1) + 0);
                GameInfo correctGame = gameList.get(randomNumber);

                boolean genreCorrect = false;
                boolean typeCorrect = false;
                boolean engineCorrect = false;
                boolean companyCorrect = false;
                boolean platformCorrect = false;
                boolean correct = false;
                System.out.println();
                while (correct == false) {
                    System.out.println("ROUND " + (rounds + 1));
                    System.out.println("\nGuess the game from this list! ");
                    for (int i = 0; i < gameList.size(); i ++) {
                        System.out.println(i+1 + ") " + gameList.get(i).getName());
                        System.out.println("Summary: " + gameList.get(i).getSummary());
                        System.out.println();
                    }

                    if (genreCorrect) {
                        System.out.println("Genre: " + "✅" + " " + correctGame.getGenreName());
                    }
                    else {
                        System.out.println("Genre: " + "❌");
                    }

                    if (typeCorrect) {
                        System.out.println("Type: " + "✅" + " " + correctGame.getGameType());
                    }
                    else {
                        System.out.println("Type: " + "❌");
                    }

                    if (engineCorrect) {
                        System.out.println("Game engine: " + "✅" + " " + correctGame.getGameEngineName());
                    }
                    else {
                        System.out.println("Game engine: " + "❌");
                    }

                    if (companyCorrect) {
                        System.out.println("Game company: " + "✅" + " " + correctGame.getCompanyName());
                    }
                    else {
                        System.out.println("Game company: " + "❌");
                    }

                    if (platformCorrect) {
                        System.out.println("Platform: " + "✅" + " " + correctGame.getplatformName());
                    }
                    else {
                        System.out.println("Platform: " + "❌");
                    }

                    System.out.println();
                    System.out.println("Input: ");
                    int guess = scan.nextInt();
                    scan.nextLine();
                    if (!gameList.get(guess - 1).getName().equals(correctGame.getName())) {
                        System.out.println();
                        System.out.println("Incorrect!");
                        roundPoints --;
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println();
                    }
                    else if (gameList.get(guess - 1).getName().equals(correctGame.getName())) {
                        System.out.println();
                        System.out.println("Correct!");
                        totalPoints += roundPoints;
                        System.out.println("You earned "+ roundPoints + " points this round!");
                        correct = true;
                        System.out.println();
                    }
                    if (gameList.get(guess - 1).getGenreName().equals(correctGame.getGenreName())) {
                        genreCorrect = true;
                    }
                    else {
                        genreCorrect = false;
                    }
                    if (gameList.get(guess - 1).getGameType().equals(correctGame.getGameType())) {
                        typeCorrect = true;
                    }
                    else {
                        typeCorrect = false;
                    }
                    if (gameList.get(guess - 1).getGameEngineName().equals(correctGame.getGameEngineName())) {
                        engineCorrect = true;
                    }
                    else {
                        engineCorrect = false;
                    }
                    if (gameList.get(guess - 1).getCompanyName().equals(correctGame.getCompanyName())) {
                        companyCorrect = true;
                    }
                    else {
                        companyCorrect = false;
                    }
                    if (gameList.get(guess - 1).getplatformName().equals(correctGame.getplatformName())) {
                        platformCorrect = true;
                    }
                    else {
                        platformCorrect = false;
                    }
                }

                System.out.println();
                System.out.println("Info about " + correctGame.getName() + ":");
                System.out.println();
                System.out.println("Genre: " + correctGame.getGenreName());
                System.out.println("Type: " + correctGame.getGameType());
                System.out.println("Game engine: " + correctGame.getGameEngineName());
                System.out.println("Game company: " + correctGame.getCompanyName());
                System.out.println("Platform: " + correctGame.getplatformName());
                System.out.println("Summary:" + correctGame.getSummary());
                System.out.println();

                System.out.println("Press x to continue or press n to exit");
                input = scan.nextLine();
                if (input.equals("n")) {
                    break;
                }
                rounds ++;
                System.out.println("Choose an option:\n1)7 random games to guess\n2)7 games to guess from a franchise");
                option = scan.nextInt();
                scan.nextLine();
                //i learned this from https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
                if (option == 1) {
                    System.out.print("Loading");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.print(".");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.print(".");
                    TimeUnit.SECONDS.sleep(1);
                    output = API.getData("https://api.igdb.com/v4/games/count", bearerCode, clientId, "fields id; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 1;");
                    TimeUnit.SECONDS.sleep(1);
                    gameCountObject = new JSONObject(output);
                    gameCount = gameCountObject.getInt("count");
                    offset = (int) (Math.random() * ((gameCount - 7) - 0 + 1) + 0);
                    output = API.getData("https://api.igdb.com/v4/games", bearerCode, clientId, "fields name, genres.*, game_engines.*, involved_companies.*, platforms.*, game_type, summary; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 7; offset " + offset + ";");
                    // i learned this from https://www.tutorialspoint.com/how-to-write-create-a-json-array-using-java
                    gameArray = new JSONArray(output);
                }
                else if (option == 2){

                    System.out.println("Type in a popular franchise: ");
                    franchiseChoice = scan.nextLine();
                    System.out.print("Loading");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.print(".");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.print(".");
                    TimeUnit.SECONDS.sleep(1);                
                    output = API.getData("https://api.igdb.com/v4/games/count", bearerCode, clientId, "fields id; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 1; search \""+ franchiseChoice + "\";");
                    TimeUnit.SECONDS.sleep(1);
                    gameCountObject = new JSONObject(output);
                    gameCount = gameCountObject.getInt("count");
                    offset = (int) (Math.random() * ((gameCount - 7) - 0 + 1) + 0);
                    output = API.getData("https://api.igdb.com/v4/games", bearerCode, clientId, "fields name, genres.*, game_engines.*, involved_companies.*, platforms.*, game_type, summary; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 7; offset " + offset + "; search \"" + franchiseChoice + "\";");
                    // i learned this from https://www.tutorialspoint.com/how-to-write-create-a-json-array-using-java
                    gameArray = new JSONArray(output);   
                }
                else{
                    System.out.println("That's not an option!");
                    System.out.println("Defaulted to 7 random games");
                    System.out.print("Loading");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.print(".");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.print(".");
                    TimeUnit.SECONDS.sleep(1);                
                    output = API.getData("https://api.igdb.com/v4/games/count", bearerCode, clientId, "fields id; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 1;");
                    TimeUnit.SECONDS.sleep(1);
                    gameCountObject = new JSONObject(output);
                    gameCount = gameCountObject.getInt("count");
                    offset = (int) (Math.random() * ((gameCount - 7) - 0 + 1) + 0);
                    output = API.getData("https://api.igdb.com/v4/games", bearerCode, clientId, "fields name, genres.*, game_engines.*, involved_companies.*, platforms.*, game_type, summary; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 7; offset " + offset + ";");
                    // i learned this from https://www.tutorialspoint.com/how-to-write-create-a-json-array-using-java
                    gameArray = new JSONArray(output);
                }
                System.out.print(".");
                TimeUnit.SECONDS.sleep(1);
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("Total rounds: " + (rounds + 1));
            System.out.println("Total points: " + totalPoints);
            double average = (double) totalPoints / (rounds + 1) ;
            System.out.println("Average points per round: " + average);
        } catch (Exception e) {
            System.err.println("Failed to get token: " + e.getMessage());
            e.printStackTrace();
        }
    }
}