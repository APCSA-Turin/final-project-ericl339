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
            // accesses the authorization code, client id, and client secret from twitchGetter.java
            String bearerCode = twitchGetter.getBearer(); // bearer code
            String clientId = twitchGetter.getClientId(); // client id
            String clientSecret = twitchGetter.getClientSecret(); // client secret

            // creates a ascanner object and prompts the user to press x to start the game
            Scanner scan = new Scanner(System.in);
            System.out.println("Welcome! Press x to start");
            String input = scan.nextLine();
            // prompts the user to choose either to guess from 7 randomly generated games or 7 sorted ones
            System.out.println("Choose an option:\n1)7 random games to guess\n2)7 games to guess from a franchise");
            int option = scan.nextInt();
            scan.nextLine();
            // creates the output string which is the string outputed from the api
            String output = null;
            // creates an object that counts how many games are called from the api
            JSONObject gameCountObject = null;
            // creates gameCount to store the count from gameCountObject
            int gameCount;
            // creates an offset in order to generate randomized games
            int offset;
            // creates a JSONArray that will later be filled with the games outputed by the api
            JSONArray gameArray = null;
            // creates a franchiseChoice which stores the user's choice if they choose the second option
            String franchiseChoice = null;
            // initializes total points to 0
            int totalPoints = 0;
            // creates how many rounds there are
            int rounds = 0;
            // if player chooses option 1
            if (option == 1) {
                // prints out loading
                System.out.print("Loading");
                // calls the api using the link, an authorization code, client id, and the commands to igdb's api. gets specifically the id which is the count while making sure that all these other parts are not absent
                output = API.getData("https://api.igdb.com/v4/games/count", bearerCode, clientId, "fields id; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 1;");
                // waits 1 second so it wont result in error 429
                TimeUnit.SECONDS.sleep(1);
                // stores the output into the json object
                gameCountObject = new JSONObject(output);
                // stores the call to count in json object to gameCount
                gameCount = gameCountObject.getInt("count");
                // creates an offset to use in the next call
                offset = (int) (Math.random() * ((gameCount - 7) - 0 + 1) + 0);
                // calls the api with specific fields, how many games are called, and limits games without the desired properties
                output = API.getData("https://api.igdb.com/v4/games", bearerCode, clientId, "fields name, genres.*, game_engines.*, involved_companies.*, platforms.*, game_type, summary; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 7; offset " + offset + ";");
                // i learned this from https://www.tutorialspoint.com/how-to-write-create-a-json-array-using-java
                gameArray = new JSONArray(output);
            }
            // if the player chooses option 2
            else if (option == 2){
                // prompts the player to choose a popular franchise and stores it
                System.out.println("Type in a popular franchise: ");
                franchiseChoice = scan.nextLine();
                System.out.print("Loading");
                // makes a call to the api to get how many total games are in that franchise
                output = API.getData("https://api.igdb.com/v4/games/count", bearerCode, clientId, "fields id; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 1; search \""+ franchiseChoice + "\";");
                TimeUnit.SECONDS.sleep(1);
                // stores the output to a json object and calls it to store count into gamecount
                gameCountObject = new JSONObject(output);
                gameCount = gameCountObject.getInt("count");
                // makes a random offset
                offset = (int) (Math.random() * ((gameCount - 7) - 0 + 1) + 0);
                // calls the game using specific limiters and puts it into output
                output = API.getData("https://api.igdb.com/v4/games", bearerCode, clientId, "fields name, genres.*, game_engines.*, involved_companies.*, platforms.*, game_type, summary; where genres != null & game_engines != null & involved_companies != null & platforms != null & summary != null; limit 7; offset " + offset + "; search \"" + franchiseChoice + "\";");
                // i learned this from https://www.tutorialspoint.com/how-to-write-create-a-json-array-using-java
                gameArray = new JSONArray(output);   
            }
            // SAME AS OPTION 1
            // runs if the player puts anything other than 1 or 2
            // defaults it to option 1
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
            // waits 3 seconds to make sure error 429 wont pop up
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
            TimeUnit.SECONDS.sleep(1);
            // runs while the player presses x
            while (input.equals("x")) {
                // waits one more second just to make sure no error pops up
                TimeUnit.SECONDS.sleep(1);
                // creates an arraylist to store the game and its info
                ArrayList<GameInfo> gameList = new ArrayList<>();
                // iterates through the json array to get info and store it into the gamelist
                for (int i = 0; i < gameArray.length(); i ++) {
                    // creates a json object specifically for the game at index i
                    JSONObject game = gameArray.getJSONObject(i);

                    // creates an array list for genre, engine, company, and platform because there are multiple
                    // stores category, name, and summary through a key to the specific game
                    ArrayList<String> genreName = new ArrayList<>();
                    int categoryId = game.getInt("game_type");
                    ArrayList<String> gameEngineName = new ArrayList<>();
                    ArrayList<String> companyName = new ArrayList<>();
                    ArrayList<String> platformName = new ArrayList<>();
                    String name = game.getString("name");
                    String summary = game.getString("summary");
                    // calls another website of the api in order to translate the id of the category into an actual string
                    String output1 = API.getData("https://api.igdb.com/v4/game_types", bearerCode, clientId, "fields *; where id = (" + categoryId + ");");
                    // puts the output into a json array
                    JSONArray gameTypeArray = new JSONArray(output1);
                    // gets the json object at index 0
                    JSONObject typeInfo = gameTypeArray.getJSONObject(0);
                    // gets the type of game with the key type and stores it into gameType
                    String gameType = typeInfo.getString("type");

                    // creates a json array and store the genre array into it
                    JSONArray genres = game.getJSONArray("genres");
                    // iterates through genre array
                    for (int j = 0; j < genres.length(); j ++) {
                        // creates a new object to get the genre info
                        JSONObject k = genres.getJSONObject(j);
                        // adds the genre name to the genreName array
                        genreName.add(k.getString("name"));
                    }

                    // creates a json array and store the engine array into it
                    JSONArray engines = game.getJSONArray("game_engines");
                    // iterates through the engine array
                    for (int j = 0; j < engines.length(); j ++) {
                        // creates a new object to get the engine info
                        JSONObject k = engines.getJSONObject(j);
                        // adds the engine name to the gameEngineName array
                        gameEngineName.add(k.getString("name"));
                    }

                    // creates a json array aand store the company array into it
                    JSONArray companies = game.getJSONArray("involved_companies");
                    //iterates through the company array
                    for (int j = 0; j < companies.length(); j ++) {
                        // creates a new object to get the company info
                        JSONObject k = companies.getJSONObject(j);
                        // retrieves the company id
                        int companyId = k.getInt("company");
                        // calls to an api that has all the company info with the specific id and stores it into a string
                        String output2 = API.getData("https://api.igdb.com/v4/companies", bearerCode, clientId, "fields name; where id = (" + companyId + ");");
                        // creates another output that takes in output2 and makes it into a json array
                        JSONArray outputCompany = new JSONArray(output2);
                        // gets the company
                        JSONObject y = outputCompany.getJSONObject(0);
                        // gets the company's name
                        companyName.add(y.getString("name"));
                    }

                    // creates a json array and store the platform array into it
                    JSONArray platforms = game.getJSONArray("platforms");
                    // iterates through the company array
                    for (int j = 0; j < platforms.length(); j ++) {
                        // creates a new object to get the platform info
                        JSONObject k = platforms.getJSONObject(j);
                        // gets the platform name and stores it into platformName array
                        platformName.add(k.getString("name"));
                    }

                    // calls the GameInfo class and creates item which is the game and its details
                    GameInfo item = new GameInfo(genreName,gameType,gameEngineName,companyName,platformName,name,summary);
                    // adds the game and its details to the gameList
                    gameList.add(item);
                }

                if (gameList.size() == 0) {
                    System.out.println("You can't do that");
                    break;
                }

                // default points awarded
                int roundPoints = 7;
                // gets a random number and chooses which game is the answer through it
                int randomNumber = (int) (Math.random() * (gameList.size() - 1 + 0 + 1) + 0);
                GameInfo correctGame = gameList.get(randomNumber);

                // sets all these boolean to false which are the hints that the player will get as they play
                boolean genreCorrect = false;
                boolean typeCorrect = false;
                boolean engineCorrect = false;
                boolean companyCorrect = false;
                boolean platformCorrect = false;
                // win condition
                boolean correct = false;
                System.out.println();

                // creates 
                ArrayList<String> totalGenreCorrect = new ArrayList<>();
                ArrayList<String> totalEngineCorrect = new ArrayList<>();
                ArrayList<String> totalCompanyCorrect = new ArrayList<>();
                ArrayList<String> totalPlatformCorrect = new ArrayList<>();

                while (correct == false) {
                    System.out.println("ROUND " + (rounds + 1));
                    System.out.println("\nGuess the game from this list using your numpad! ");
                    for (int i = 0; i < gameList.size(); i ++) {
                        System.out.println(i+1 + ") " + gameList.get(i).getName());
                        System.out.println("Summary: " + gameList.get(i).getSummary());
                        System.out.println();
                    }

                    if (genreCorrect) {
                        System.out.println("Genre: " + "✅" + " " + totalGenreCorrect);
                    }
                    else {
                        System.out.println("Genre: " + "❌");
                    }
                    totalGenreCorrect = new ArrayList<>();
                    genreCorrect = false;

                    if (typeCorrect) {
                        System.out.println("Type: " + "✅" + " " + correctGame.getGameType());
                    }
                    else {
                        System.out.println("Type: " + "❌");
                    }

                    if (engineCorrect) {
                        System.out.println("Game engine: " + "✅" + " " + totalEngineCorrect);
                    }
                    else {
                        System.out.println("Game engine: " + "❌");
                    }
                    totalEngineCorrect = new ArrayList<>();
                    engineCorrect = false;

                    if (companyCorrect) {
                        System.out.println("Game company: " + "✅" + " " + totalCompanyCorrect);
                    }
                    else {
                        System.out.println("Game company: " + "❌");
                    }
                    totalCompanyCorrect = new ArrayList<>();
                    companyCorrect = false;

                    if (platformCorrect) {
                        System.out.println("Platform: " + "✅" + " " + totalPlatformCorrect);
                    }
                    else {
                        System.out.println("Platform: " + "❌");
                    }
                    totalPlatformCorrect = new ArrayList<>();
                    platformCorrect = false;

                    System.out.println();
                    System.out.println("Input using numpad: ");
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

                    for (int i = 0; i < gameList.get(guess - 1).getGenreName().size(); i ++) {
                        String guessGenre = gameList.get(guess - 1).getGenreName().get(i);
                        for (int j = 0; j < correctGame.getGenreName().size(); j ++) {
                            String answerGenre = correctGame.getGenreName().get(j);
                            if (answerGenre.equals(guessGenre)) {
                                genreCorrect = true;
                                totalGenreCorrect.add(guessGenre);
                            }
                        }
                    }

                    if (gameList.get(guess - 1).getGameType().equals(correctGame.getGameType())) {
                        typeCorrect = true;
                    }
                    else {
                        typeCorrect = false;
                    }


                    for (int i = 0; i < gameList.get(guess - 1).getGameEngineName().size(); i ++) {
                        String guessEngine = gameList.get(guess - 1).getGameEngineName().get(i);
                        for (int j = 0; j < correctGame.getGameEngineName().size(); j ++) {
                            String answerEngine = correctGame.getGameEngineName().get(j);
                            if (answerEngine.equals(guessEngine)) {
                                engineCorrect = true;
                                totalEngineCorrect.add(guessEngine);
                            }
                        }
                    }

                    for (int i = 0; i < gameList.get(guess - 1).getCompanyName().size(); i ++) {
                        String guessCompany = gameList.get(guess - 1).getCompanyName().get(i);
                        for (int j = 0; j < correctGame.getCompanyName().size(); j ++) {
                            String answerCompany = correctGame.getCompanyName().get(j);
                            if (answerCompany.equals(guessCompany)) {
                                companyCorrect = true;
                                totalCompanyCorrect.add(guessCompany);
                            }
                        }
                    }

                    for (int i = 0; i < gameList.get(guess - 1).getplatformName().size(); i ++) {
                        String guessPlatform = gameList.get(guess - 1).getplatformName().get(i);
                        for (int j = 0; j < correctGame.getplatformName().size(); j ++) {
                            String answerPlatform = correctGame.getplatformName().get(j);
                            if (answerPlatform.equals(guessPlatform)) {
                                platformCorrect = true;
                                totalPlatformCorrect.add(guessPlatform);
                            }
                        }
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
                System.out.println("------------------------------------------------------------------------");
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
                else if (option == 2) {

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
                else {
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