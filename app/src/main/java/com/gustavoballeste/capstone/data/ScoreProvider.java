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
 * Created by gustavoballeste on 24/04/17.
 */

public class ScoreProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private ScoreDBHelper mOpenHelper;

    static final int SCORE = 100;
    static final int SCORE_WITH_ID = 101;

    private static final SQLiteQueryBuilder sScoreByIDQueryBuilder = new SQLiteQueryBuilder();

    private static final String sScoreIDSelection = ScoreContract.ScoreEntry.TABLE_NAME +
            "." + ScoreContract.ScoreEntry._ID + " = ? ";
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ScoreContract.CONTENT_AUTHORITY;

        //For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, ScoreContract.SCORE_PATH, SCORE);
        matcher.addURI(authority, ScoreContract.SCORE_PATH + "/#", SCORE_WITH_ID);

        return matcher;
    }

    private Cursor getScoreWithID(Uri uri, String[] projection, String sortOrder) {
        int score_id = ScoreContract.ScoreEntry.getScoreIDFromUri(uri);

        sScoreByIDQueryBuilder.setTables(ScoreContract.ScoreEntry.TABLE_NAME);

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
        mOpenHelper = new ScoreDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;

        switch (sUriMatcher.match(uri))
        {
            case SCORE:{
                retCursor = mOpenHelper.getReadableDatabase().query(ScoreContract.ScoreEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
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
            case SCORE:
                return ScoreContract.ScoreEntry.CONTENT_TYPE;
            case SCORE_WITH_ID:
                return ScoreContract.ScoreEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        long _id = db.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, values);
        if(_id > 0){
            returnUri = ScoreContract.ScoreEntry.buildScoreUri(_id);
        }
        else {
            throw new android.database.SQLException("Failed to insert row into SCORE TABLE: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;

        if (selection == null) selection = "1";
        switch (sUriMatcher.match(uri))
        {
            case SCORE:{
                rowsDeleted = db.delete(
                        ScoreContract.ScoreEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case SCORE_WITH_ID:{
                rowsDeleted = db.delete(ScoreContract.ScoreEntry.TABLE_NAME,
                        ScoreContract.ScoreEntry._ID + " = ?", new String[]{String.valueOf(ContentUris.parseId(uri))});
                Log.d("DELETE: ", "score " + String.valueOf(ContentUris.parseId(uri)) + " is deleted");

                int fav = getContext().getContentResolver().query(
                        ScoreContract.ScoreEntry.CONTENT_URI,
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

        rowsUpdated = db.update(ScoreContract.ScoreEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}

