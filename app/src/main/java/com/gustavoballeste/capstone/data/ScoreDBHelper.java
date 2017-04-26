package com.gustavoballeste.capstone.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gustavoballeste.capstone.model.Score;

/**
 * Created by gustavoballeste on 25/04/17.
 */

public class ScoreDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trivia.db";
    private static final int DB_VERSION = 1;
    private static ScoreDBHelper mInstance;
    private final Resources mResources;

    public ScoreDBHelper(Context context) {
        //prevents external instance creation
        super(context, DATABASE_NAME, null, DB_VERSION);
        mResources = context.getResources();
    }

    private static ScoreDBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ScoreDBHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Updates values for a category.
     *
     * @param context The context this is running in.
     */
    public static void updateScore(Context context) {

        int score = getScore(context);
        score++;
        SQLiteDatabase writableDatabase = getWritableDatabase(context);
        ContentValues values = new ContentValues();
        values.put(QuestionContract.ScoreEntry.COLUMN_SCORE, score);
        writableDatabase.delete(QuestionContract.ScoreEntry.TABLE_NAME,null,null);
        writableDatabase.insert(QuestionContract.ScoreEntry.TABLE_NAME, null, values);
    }


    private static SQLiteDatabase getReadableDatabase(Context context) {
        return getInstance(context).getReadableDatabase();
    }

    private static SQLiteDatabase getWritableDatabase(Context context) {
        return getInstance(context).getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SCORE_TABLE = "CREATE TABLE " + QuestionContract.ScoreEntry.TABLE_NAME + " (" +
                QuestionContract.ScoreEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionContract.ScoreEntry.COLUMN_SCORE + " INTEGER NOT NULL)";

        db.execSQL(SQL_CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* no-op */
    }

    public static int getScore(Context context) {
        SQLiteDatabase readableDatabase = getReadableDatabase(context);
        Cursor data = readableDatabase
                .query(QuestionContract.ScoreEntry.TABLE_NAME, null, null,
                        null, null, null, null);
        data.moveToFirst();
        Score score = new Score(data);
        int total = score.getTotal();
        return total;
    }
}
