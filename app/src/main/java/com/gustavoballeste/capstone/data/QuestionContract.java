package com.gustavoballeste.capstone.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.security.PublicKey;

/**
 * Created by gustavoballeste on 18/04/17.
 */

public class QuestionContract {
    public static final String CONTENT_AUTHORITY = "com.gustavoballeste.capstone";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String QUESTION_PATH = "question";

    public static final class QuestionEntry implements BaseColumns {
        public static final String TABLE_NAME = "question";
        public static final String COLUMN_STATEMENT = "statement";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_CORRECT_ANSWER = "correct_answer";
        public static final String COLUMN_INCORRECT_ANSWER1 = "incorrect_answer_1";
        public static final String COLUMN_INCORRECT_ANSWER2 = "incorrect_answer_2";
        public static final String COLUMN_INCORRECT_ANSWER3 = "incorrect_answer_3";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(QUESTION_PATH).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + QUESTION_PATH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + QUESTION_PATH;

        public static Uri buildQuestionUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static int getQuestionIDFromUri(Uri uri){
            return Integer.parseInt(uri.getPathSegments().get(1));
        }
    }
}
