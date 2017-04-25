package com.gustavoballeste.capstone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by gustavoballeste on 17/04/17.
 */

public class QuestionActivity extends AppCompatActivity {
    static final int NUM_ITEMS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        getSupportActionBar().setTitle(getIntent().getExtras().getString("category"));

    }

}