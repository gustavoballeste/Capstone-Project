package com.gustavoballeste.capstone.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gustavoballeste on 18/04/17.
 */

public class QuestionDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "trivia.db";
    public static final int SCORE_INITIAL_VALUE = 0;

    public QuestionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " + QuestionContract.QuestionEntry.TABLE_NAME + " (" +
            QuestionContract.QuestionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuestionContract.QuestionEntry.COLUMN_QUESTION_NUMBER + " TEXT NOT NULL, " +
            QuestionContract.QuestionEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
            QuestionContract.QuestionEntry.COLUMN_TYPE + " TEXT, " +
            QuestionContract.QuestionEntry.COLUMN_DIFFICULTY + " TEXT, " +
            QuestionContract.QuestionEntry.COLUMN_STATEMENT + " TEXT, " +
            QuestionContract.QuestionEntry.COLUMN_CORRECT_ANSWER + " TEXT, " +
            QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER1 + " TEXT, " +
            QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER2 + " TEXT, " +
            QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER3 + " TEXT, " +
            QuestionContract.QuestionEntry.COLUMN_RESULT + " TEXT)";

        db.execSQL(SQL_CREATE_QUESTION_TABLE);

        final String SQL_CREATE_SCORE_TABLE = "CREATE TABLE " + QuestionContract.ScoreEntry.TABLE_NAME + " (" +
                QuestionContract.ScoreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionContract.ScoreEntry.COLUMN_SCORE + " INTEGER NOT NULL)";

        db.execSQL(SQL_CREATE_SCORE_TABLE);

        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionContract.ScoreEntry.COLUMN_SCORE, SCORE_INITIAL_VALUE);
        db.insert(QuestionContract.ScoreEntry.TABLE_NAME,null,contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionContract.QuestionEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionContract.ScoreEntry.TABLE_NAME);
        onCreate(db);
    }
}
