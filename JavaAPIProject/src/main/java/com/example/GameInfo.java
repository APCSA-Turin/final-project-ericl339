package com.example;

public class GameInfo {
    private ArrayList<Integer> genreId;
    private int categoryId;
    private ArrayList<Integer> gameEngineId;
    private ArrayList<Integer> companyId;
    private ArrayList<Integer> platformId;
    private String name;
    private String summary;

    public GameInfo(ArrayList<Integer> genreId, int categoryId, ArrayList<Integer> gameEngineId, ArrayList<Integer> companyId, ArrayList<Integer> platformId, String name, String summary) {
        this.genreId = genreId;
        this.categoryId = categoryId;
        this.gameEngineId = gameEngineId;
        this.companyId = companyId;
        this.platformId = platformId;
        this.name = name;
        this.summary = summary;
    }
}