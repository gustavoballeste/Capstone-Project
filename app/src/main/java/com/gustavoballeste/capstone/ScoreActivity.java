package com.gustavoballeste.capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        int score;
        String roundScore;
        String scoreString;

        final String category;
        final int categoryCode;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView scoreTextView = (TextView)findViewById(R.id.score_text_view);
        score = ScoreDBHelper.getScore(this);
        scoreString = Integer.toString(score);
        scoreTextView.setText(scoreString);

        category = getIntent().getExtras().getString("category");
        categoryCode = getIntent().getExtras().getInt("category_code");
        roundScore = getIntent().getExtras().getString("round_score");


        TextView categoryTextView = (TextView)findViewById(R.id.score_category_text_view);
        categoryTextView.setText(category);

        TextView roundScoreTextView = (TextView)findViewById(R.id.round_score_text_view);
        roundScoreTextView.setText(roundScore);

        Button startAgainButton = (Button)findViewById(R.id.start_again);
        startAgainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, QuestionActivity.class)
                    .putExtra("category", category)
                    .putExtra("category_code", categoryCode);
                startActivity(intent);
            }
        });

        Button chooseCategoryButton = (Button)findViewById(R.id.choose_category);
        chooseCategoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}