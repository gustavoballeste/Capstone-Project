package com.gustavoballeste.capstone.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by gustavoballeste on 18/04/17.
 */

public class QuestionProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private QuestionDBHelper mOpenHelper;

    static final int QUESTION = 100;
    static final int QUESTION_WITH_ID = 101;
    static final int SCORE = 102;
    static final int SCORE_WITH_ID = 103;

    private static final SQLiteQueryBuilder sQuestionByIDQueryBuilder = new SQLiteQueryBuilder();
    private static final SQLiteQueryBuilder sScoreByIDQueryBuilder = new SQLiteQueryBuilder();

    private static final String sQuestionIDSelection = QuestionContract.QuestionEntry.TABLE_NAME +
                                                    "." + QuestionContract.QuestionEntry._ID + " = ? ";
    private static final String sScoreIDSelection = QuestionContract.ScoreEntry.TABLE_NAME +
            "." + QuestionContract.ScoreEntry._ID + " = ? ";

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = QuestionContract.CONTENT_AUTHORITY;

        //For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, QuestionContract.QUESTION_PATH, QUESTION);
        matcher.addURI(authority, QuestionContract.QUESTION_PATH + "/#", QUESTION_WITH_ID);
        matcher.addURI(authority, QuestionContract.SCORE_PATH, SCORE);
        matcher.addURI(authority, QuestionContract.SCORE_PATH + "/#", SCORE_WITH_ID);

        return matcher;
    }

    private Cursor getQuestionWithID(Uri uri, String[] projection, String sortOrder) {
        int question_id = QuestionContract.QuestionEntry.getQuestionIDFromUri(uri);

        sQuestionByIDQueryBuilder.setTables(QuestionContract.QuestionEntry.TABLE_NAME);

        return sQuestionByIDQueryBuilder.query(mOpenHelper.getWritableDatabase(),
                projection,
                sQuestionIDSelection,
                new String[]{Integer.toString(question_id)},
                null,
                null,
                sortOrder);
    }

    private Cursor getScoreWithID(Uri uri, String[] projection, String sortOrder) {
        int score_id = QuestionContract.ScoreEntry.getScoreIDFromUri(uri);

        sScoreByIDQueryBuilder.setTables(QuestionContract.ScoreEntry.TABLE_NAME);

        return sScoreByIDQueryBuilder.query(mOpenHelper.getWritableDatabase(),
                projection,
                sScoreIDSelection,
                new String[]{Integer.toString(score_id)},
                null,
                null,
                sortOrder);
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new QuestionDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;

        switch (sUriMatcher.match(uri))
        {
            case QUESTION:{
                retCursor = mOpenHelper.getReadableDatabase().query(QuestionContract.QuestionEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case QUESTION_WITH_ID:{
                retCursor = getQuestionWithID(uri, projection, sortOrder);
                break;
            }
            case SCORE:{
                retCursor = mOpenHelper.getReadableDatabase().query(QuestionContract.ScoreEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case SCORE_WITH_ID:{
                retCursor = getScoreWithID(uri, projection, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match){
            case QUESTION:
                return QuestionContract.QuestionEntry.CONTENT_TYPE;
            case QUESTION_WITH_ID:
                return QuestionContract.QuestionEntry.CONTENT_ITEM_TYPE;
            case SCORE:
                return QuestionContract.ScoreEntry.CONTENT_TYPE;
            case SCORE_WITH_ID:
                return QuestionContract.ScoreEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case QUESTION:{
                long _id = db.insert(QuestionContract.QuestionEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = QuestionContract.QuestionEntry.buildQuestionUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into QUESTION TABLE: " + uri);
                }
                break;
            }
            case SCORE: {
                long _id = db.insert(QuestionContract.ScoreEntry.TABLE_NAME, null, values);
                if(_id > 0){
                    returnUri = QuestionContract.ScoreEntry.buildScoreUri(_id);
                }
                else {
                    throw new android.database.SQLException("Failed to insert row into SCORE TABLE: " + uri);
                }
                break;
            }
            default:
                throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match){
            case QUESTION:{
                db.beginTransaction();
                int retCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(QuestionContract.QuestionEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            retCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return retCount;
            }
            case SCORE:{
                db.beginTransaction();
                int retCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(QuestionContract.ScoreEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            retCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return retCount;
            }
            default:
                return super.bulkInsert(uri, values);
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;

        if (selection == null) selection = "1";
        switch (sUriMatcher.match(uri))
        {
            case QUESTION:{
                rowsDeleted = db.delete(
                        QuestionContract.QuestionEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case QUESTION_WITH_ID:{
                rowsDeleted = db.delete(QuestionContract.QuestionEntry.TABLE_NAME,
                        QuestionContract.QuestionEntry._ID + " = ?", new String[]{String.valueOf(ContentUris.parseId(uri))});
                Log.d("DELETE: ", "question " + String.valueOf(ContentUris.parseId(uri)) + " is deleted");

                int fav = getContext().getContentResolver().query(
                        QuestionContract.QuestionEntry.CONTENT_URI,
                        null, null, null, null).getCount();

                Log.d("SUM: ", "There're " + fav + " questions");
            }
            case SCORE:{
                rowsDeleted = db.delete(
                        QuestionContract.ScoreEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case SCORE_WITH_ID:{
                rowsDeleted = db.delete(QuestionContract.ScoreEntry.TABLE_NAME,
                        QuestionContract.ScoreEntry._ID + " = ?", new String[]{String.valueOf(ContentUris.parseId(uri))});
                Log.d("DELETE: ", "score " + String.valueOf(ContentUris.parseId(uri)) + " is deleted");

                int fav = getContext().getContentResolver().query(
                        QuestionContract.ScoreEntry.CONTENT_URI,
                        null, null, null, null).getCount();

                Log.d("SUM: ", "There're " + fav + " scores");
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated;

        switch (sUriMatcher.match(uri)) {
            case QUESTION:
                rowsUpdated = db.update(QuestionContract.QuestionEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case SCORE:
                rowsUpdated = db.update(QuestionContract.ScoreEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
