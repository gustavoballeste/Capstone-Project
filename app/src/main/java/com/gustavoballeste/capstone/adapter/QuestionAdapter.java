package com.gustavoballeste.capstone.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gustavoballeste.capstone.QuestionFragment;
import com.gustavoballeste.capstone.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.gustavoballeste.capstone.R.id.answer2;

/**
 * Created by gustavoballeste on 18/04/17.
 */

public class QuestionAdapter extends CursorAdapter {


    public QuestionAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_question, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());

        String statement = cursor.getString(QuestionFragment.COL_STATEMENT);
        TextView statementTextView = (TextView) view.findViewById(R.id.statement);
        statementTextView.setText(statement);

        List<String> answersList = new ArrayList<>();
        answersList.add(cursor.getString(QuestionFragment.COL_CORRECT_ANSWER));
        answersList.add(cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER1));
        answersList.add(cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER2));
        answersList.add(cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER3));

        TextView answer1TextView = (TextView) view.findViewById(R.id.answer1);
        TextView answer2TextView = (TextView) view.findViewById(R.id.answer2);
        TextView answer3TextView = (TextView) view.findViewById(R.id.answer3);
        TextView answer4TextView = (TextView) view.findViewById(R.id.answer4);

        Collections.shuffle(answersList);
        answer1TextView.setText(answersList.get(0));
        answer2TextView.setText(answersList.get(1));
        answer3TextView.setText(answersList.get(2));
        answer4TextView.setText(answersList.get(3));

    }

}