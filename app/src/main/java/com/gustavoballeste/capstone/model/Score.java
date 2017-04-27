package com.gustavoballeste.capstone.model;

import android.database.Cursor;

import com.gustavoballeste.capstone.ScoreActivity;

/**
 * Created by gustavoballeste on 17/04/17.
 */

public class Score {
    private int id;
    private int total;

    public int getId() { return id; }
    public int getTotal() { return total; }

    public Score(Cursor cursor) {
        this.id = cursor.getInt(ScoreActivity.COL_ID);
        this.total = cursor.getInt(ScoreActivity.COL_TOTAL);
    }

}
