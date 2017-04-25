package com.gustavoballeste.capstone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gustavoballeste on 24/04/17.
 */

public class ScoreDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "trivia.db";

    public ScoreDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SCORE_TABLE = "CREATE TABLE " + ScoreContract.ScoreEntry.TABLE_NAME + " (" +
                ScoreContract.ScoreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ScoreContract.ScoreEntry.COLUMN_SCORE + " TEXT NOT NULL ";

        db.execSQL(SQL_CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ScoreContract.ScoreEntry.TABLE_NAME);
        onCreate(db);
    }
}
