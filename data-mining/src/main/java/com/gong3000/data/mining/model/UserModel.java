package com.gong3000.data.mining.model;

import java.util.LinkedHashMap;

public class UserModel {

    private String name;
    private LinkedHashMap<String, Double> scoreMap;

    public UserModel(String name, LinkedHashMap<String, Double> scoreMap) {
        this.name = name;
        this.scoreMap = scoreMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedHashMap<String, Double> getScoreMap() {
        return scoreMap;
    }

    public void setScoreMap(LinkedHashMap<String, Double> scoreMap) {
        this.scoreMap = scoreMap;
    }
}