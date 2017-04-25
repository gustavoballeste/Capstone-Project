package com.gustavoballeste.capstone.query;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.gustavoballeste.capstone.ScoreActivity;
import com.gustavoballeste.capstone.data.ScoreContract;
import org.json.JSONException;

/**
 * Created by gustavoballeste on 18/04/17.
 */

public class UpdateScore {

    final String LOG_TAG = ScoreActivity.class.getSimpleName();

    private final Context mContext;

    public UpdateScore(Context mContext) {
        this.mContext = mContext;

    }

    public void scores() throws JSONException {

        final String score = "score";
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ScoreContract.ScoreEntry.COLUMN_SCORE, 1);

            int updated = mContext.getContentResolver().update(ScoreContract.ScoreEntry.CONTENT_URI,contentValues,null,null);

            Log.d(LOG_TAG, "Previous data wiping Complete. " + updated + " updated");
        }
        catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

}
