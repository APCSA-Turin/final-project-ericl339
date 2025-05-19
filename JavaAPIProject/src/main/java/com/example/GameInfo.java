package com.example;

import java.util.ArrayList;

public class GameInfo {
    private ArrayList<String> genreName;
    private String gameType;
    private ArrayList<String> gameEngineName;
    private ArrayList<String> companyName;
    private ArrayList<String> platformName;
    private String name;
    private String summary;

    public GameInfo(ArrayList<String> genreName, String gameType, ArrayList<String> gameEngineName, ArrayList<String> companyName, ArrayList<String> platformName, String name, String summary) {
        this.genreName = genreName;
        this.gameType = gameType;
        this.gameEngineName = gameEngineName;
        this.companyName = companyName;
        this.platformName = platformName;
        this.name = name;
        this.summary = summary;
    }

    public ArrayList<String> getGenreName(){return genreName;}
    public String getGameType() {return gameType;}
    public ArrayList<String> getGameEngineName(){return gameEngineName;}
    public ArrayList<String> getCompanyName(){return companyName;}
    public ArrayList<String> getplatformName(){return platformName;}
    public String getName() {return name;}
    public String getSummary() {return summary;}

}