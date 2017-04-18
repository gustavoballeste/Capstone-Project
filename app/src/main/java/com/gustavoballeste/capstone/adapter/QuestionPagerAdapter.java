package com.gustavoballeste.capstone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gustavoballeste.capstone.QuestionFragment;

/**
 * Created by gustavoballeste on 18/04/17.
 */

public  class QuestionPagerAdapter extends FragmentPagerAdapter {
    int NUM_ITEMS = 10;
    public QuestionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        return QuestionFragment.newInstance(position);
    }
}