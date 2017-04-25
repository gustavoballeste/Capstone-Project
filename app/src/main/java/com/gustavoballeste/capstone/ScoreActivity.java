package com.gustavoballeste.capstone;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.gustavoballeste.capstone.model.Score;


/**
 * Created by gustavoballeste on 24/04/17.
 */

public class ScoreActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Score score = new Score();

        TextView textView = (TextView)findViewById(R.id.score_text_view);
        textView.setText(score.getScore());

    }


}
