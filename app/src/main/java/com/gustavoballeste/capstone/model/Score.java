package com.gustavoballeste.capstone.model;

import android.database.Cursor;

/**
 * Created by gustavoballeste on 17/04/17.
 */

public class Score {
    private int id;
    private int score ;


    public int getId() { return id; }
    public int getScore() { return score; }

    public Score() {
        this.id = id;
        this.score = score;
    }

    public Score(int id, int score) {
        this.id = id;
        this.score = score;
    }

    public Score(Cursor cursor){
//        this.id = cursor.getInt(ResultActivity.COL_ID);
//        this.score = cursor.getString(ResultActivity.COL_SCORE);
    }
}
