package com.gustavoballeste.capstone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gustavoballeste.capstone.data.QuestionContract;
import com.gustavoballeste.capstone.model.Question;

/**
 * Created by gustavoballeste on 17/04/17.
 */

public class QuestionActivityFragment extends FragmentActivity {
    static final int NUM_ITEMS = 10;

    MyAdapter mAdapter;

    ViewPager mPager;

    private static final int MOVIES_LOADER = 0;

    public static final String[] QUESTION_COLUMNS = {
            QuestionContract.QuestionEntry._ID,
            QuestionContract.QuestionEntry.COLUMN_STATEMENT,
            QuestionContract.QuestionEntry.COLUMN_CATEGORY,
            QuestionContract.QuestionEntry.COLUMN_TYPE,
            QuestionContract.QuestionEntry.COLUMN_DIFFICULTY,
            QuestionContract.QuestionEntry.COLUMN_CORRECT_ANSWER,
            QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER1,
            QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER2,
            QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER3,
    };

    public static final int COL_ID = 0;
    public static final int COL_STATEMENT = 1;
    public static final int COL_CATEGORY = 2;
    public static final int COL_TYPE = 3;
    public static final int COL_DIFFICULTY = 4;
    public static final int COL_CORRECT_ANSWER = 5;
    public static final int COL_INCORRECT_ANSWER1 = 6;
    public static final int COL_INCORRECT_ANSWER2 = 7;
    public static final int COL_INCORRECT_ANSWER3 = 8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        TextView textView = (TextView)findViewById(R.id.category_text_view);
        textView.setText(getIntent().getExtras().getString("category"));

        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.goto_next);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPager.setCurrentItem(NUM_ITEMS-1);
            }
        });
    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
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

    public static class QuestionFragment extends Fragment {
        int mNum;

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static QuestionFragment newInstance(int num) {
            QuestionFragment f = new QuestionFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num")+1 : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
            View countTv = v.findViewById(R.id.count);
            ((TextView)countTv).setText(mNum+"/10");

            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            String[] item = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
            super.onActivityCreated(savedInstanceState);
//            setListAdapter(new ArrayAdapter<>(getActivity(),
//                    android.R.layout.simple_list_item_1, item)); //Incluir aqui um List<Question> de QuestionMock getQuestionMock()
        }


    }

}