package com.gustavoballeste.capstone.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.gustavoballeste.capstone.QuestionFragment;

/**
 * Created by gustavoballeste on 17/04/17.
 */

public class Question implements Parcelable {
    private int id;
    private String question_number;
    private String category;
    private String type;
    private String difficulty;
    private String statement;
    private String correctAnswer;
    private String incorrectAnswer1;
    private String incorrectAnswer2;
    private String incorrectAnswer3;
    private String result;

    public int getId() { return id; }
    public String getQuestionNumber() { return question_number; }
    public String getCategory() { return category; }
    public String getType() { return type; }
    public String getDifficulty() { return difficulty; }
    public String getStatement() { return statement; }
    public String getCorrectAnswer() { return correctAnswer; }
    public String getIncorrectAnswer1() { return incorrectAnswer1; }
    public String getIncorrectAnswer2() { return incorrectAnswer2; }
    public String getIncorrectAnswer3() { return incorrectAnswer3; }
    public String getResult() {return result;}

    public Question (int id, String questionNumber, String category, String type, String difficulty, String statement, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2, String incorrectAnswer3, String result) {
        this.id = id;
        this.question_number = questionNumber;
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.statement = statement;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswer1 = incorrectAnswer1;
        this.incorrectAnswer2 = incorrectAnswer2;
        this.incorrectAnswer3 = incorrectAnswer3;
        this.result = result;
    }

    public Question(Cursor cursor){

        this.id = cursor.getInt(QuestionFragment.COL_ID);
        this.question_number = cursor.getString(QuestionFragment.COL_QUESTION_NUMBER);
        this.category = cursor.getString(QuestionFragment.COL_CATEGORY);
        this.type = cursor.getString(QuestionFragment.COL_TYPE);
        this.difficulty = cursor.getString(QuestionFragment.COL_DIFFICULTY);
        this.statement = cursor.getString(QuestionFragment.COL_STATEMENT);
        this.correctAnswer = cursor.getString(QuestionFragment.COL_CORRECT_ANSWER);
        this.incorrectAnswer1 = cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER1);
        this.incorrectAnswer2 = cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER2);
        this.incorrectAnswer3 = cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER3);
        this.result = cursor.getString(QuestionFragment.COL_RESULT);
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question_number);
        dest.writeString(category);
        dest.writeString(type);
        dest.writeString(difficulty);
        dest.writeString(statement);
        dest.writeString(correctAnswer);
        dest.writeString(incorrectAnswer1);
        dest.writeString(incorrectAnswer2);
        dest.writeString(incorrectAnswer3);
        dest.writeString(result);
    }

    private Question(Parcel in) {
        id = in.readInt();
        question_number = in.readString();
        category = in.readString();
        type = in.readString();
        difficulty = in.readString();
        statement = in.readString();
        correctAnswer = in.readString();
        incorrectAnswer1 = in.readString();
        incorrectAnswer2 = in.readString();
        incorrectAnswer3 = in.readString();
        result = in.readString();
    }
}
