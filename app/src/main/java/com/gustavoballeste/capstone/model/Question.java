package com.gustavoballeste.capstone.model;

/**
 * Created by gustavoballeste on 17/04/17.
 */

public class Question {
    private int id;
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correctAnswer;
    private String incorrectAnswer1;
    private String incorrectAnswer2;
    private String incorrectAnswer3;

    public Question (int id, String category, String type, String difficulty, String question, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2, String incorrectAnswer3) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswer1 = incorrectAnswer1;
        this.incorrectAnswer2 = incorrectAnswer2;
        this.incorrectAnswer3 = incorrectAnswer3;
    }

    public int getId() { return id; }
    public String getCategory() { return category; }
    public String getType() { return type; }
    public String getDifficulty() { return difficulty; }
    public String getQuestion() { return question; }
    public String getCorrectAnswer() { return correctAnswer; }
    public String getIncorrectAnswer1() { return incorrectAnswer1; }
    public String getIncorrectAnswer2() { return incorrectAnswer2; }
    public String getIncorrectAnswer3() { return incorrectAnswer3; }
}
