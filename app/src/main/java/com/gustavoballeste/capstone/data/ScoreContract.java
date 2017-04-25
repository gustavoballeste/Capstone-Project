package com.gustavoballeste.capstone.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by gustavoballeste on 24/04/17.
 */

public class ScoreContract {
    public static final String CONTENT_AUTHORITY = "com.gustavoballeste.capstone";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String SCORE_PATH = "score";

    public static final class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "score";
        public static final String COLUMN_SCORE = "score";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(SCORE_PATH).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + SCORE_PATH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + SCORE_PATH;

        public static Uri buildScoreUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static int getScoreIDFromUri(Uri uri){
            return Integer.parseInt(uri.getPathSegments().get(1));
        }
    }
}
