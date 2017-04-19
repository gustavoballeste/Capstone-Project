package com.gustavoballeste.capstone;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gustavoballeste.capstone.adapter.QuestionPagerAdapter;

/**
 * Created by gustavoballeste on 17/04/17.
 */

public class QuestionActivity extends AppCompatActivity {
    static final int NUM_ITEMS = 10;

    QuestionPagerAdapter mAdapter;

    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);

        mAdapter = new QuestionPagerAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        getSupportActionBar().setTitle(getIntent().getExtras().getString("category"));

        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.goto_next);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(NUM_ITEMS-1);
            }
        });
    }


}