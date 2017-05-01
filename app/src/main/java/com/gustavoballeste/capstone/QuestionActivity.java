package com.gustavoballeste.capstone;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.gustavoballeste.capstone.adapter.QuestionAdapter;

/**
 * Created by gustavoballeste on 17/04/17.
 */

public class QuestionActivity extends AppCompatActivity {
    private static FirebaseAnalytics mFirebaseAnalytics;

    private String mCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mCategory = getIntent().getExtras().getString(getString(R.string.category));
        android.support.v7.widget.Toolbar myToolbar =
                (android.support.v7.widget.Toolbar) findViewById(R.id.question_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(mCategory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home: {
                String count;
                TextView countTextView;
                countTextView = (TextView)QuestionFragment.mView.findViewById(R.id.count);
                count = (String)countTextView.getText();

                mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.count), count);
                bundle.putString(getString(R.string.category), mCategory);
                mFirebaseAnalytics.logEvent(getString(R.string.unfinished_round), bundle);
            }
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}