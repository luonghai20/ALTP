package com.haixl.ailatrieuphu.model;

public class highScore {
    String name;
    int score;

    public highScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
