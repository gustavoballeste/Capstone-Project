package com.gustavoballeste.capstone.model;

/**
 * Created by gustavoballeste on 17/04/17.
 */

public class Result {
    int id;
    int correctAnswers;

    public Result(int id, int correctAnswers) {
        this.id = id;
        this.correctAnswers = correctAnswers;
    }

    public int getId() { return id; }
    public int getCorrectAnswers() { return correctAnswers; }
}
