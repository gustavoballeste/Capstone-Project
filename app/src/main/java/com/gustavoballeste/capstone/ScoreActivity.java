package com.gustavoballeste.capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gustavoballeste.capstone.data.ScoreDBHelper;


/**
 * Created by gustavoballeste on 24/04/17.
 */

public class ScoreActivity extends Activity{

    public static final int COL_ID = 0;
    public static final int COL_TOTAL= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String roundScore;
        String totalScore;
        final String category;
        final int categoryCode;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextView totalScoreTextView = (TextView)findViewById(R.id.total_score_text_view);
        totalScore = Integer.toString(ScoreDBHelper.getScore(this));
        totalScoreTextView.setText(totalScore);
        totalScoreTextView.setContentDescription(getString(R.string.total_score_inicial_description) + totalScore + getString(R.string.points_description));

        category = getIntent().getExtras().getString(getResources().getString(R.string.category));
        categoryCode = getIntent().getExtras().getInt(getString(R.string.category_code));
        roundScore = getIntent().getExtras().getString(getString(R.string.round_score));

        TextView categoryTextView = (TextView)findViewById(R.id.score_category_text_view);
        categoryTextView.setText(category);
        categoryTextView.setContentDescription(getString(R.string.category_inicial_description) + category);

        TextView roundScoreTextView = (TextView)findViewById(R.id.round_score_text_view);
        roundScoreTextView.setText(roundScore);
        roundScoreTextView.setContentDescription(getString(R.string.round_score_inicial_description) + roundScore + getString(R.string.points_description));

        Button startAgainButton = (Button)findViewById(R.id.start_again);
        startAgainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, QuestionActivity.class)
                    .putExtra(getString(R.string.category), category)
                    .putExtra(getString(R.string.category_code), categoryCode);
                startActivity(intent);
            }
        });

        Button chooseCategoryButton = (Button)findViewById(R.id.choose_category);
        chooseCategoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}