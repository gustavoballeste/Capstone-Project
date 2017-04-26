package com.gustavoballeste.capstone;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.gustavoballeste.capstone.data.ScoreDBHelper;
import com.gustavoballeste.capstone.model.Score;


/**
 * Created by gustavoballeste on 24/04/17.
 */

public class ScoreActivity extends Activity{

    public static final int COL_ID = 0;
    public static final int COL_TOTAL= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView textView = (TextView)findViewById(R.id.score_text_view);
        int score = ScoreDBHelper.getScore(this);
        String scoreString = Integer.toString(score);
        textView.setText(scoreString);
    }
}