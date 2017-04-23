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

        String answer1 = cursor.getString(QuestionFragment.COL_CORRECT_ANSWER);
        TextView answer1TextView = (TextView) view.findViewById(R.id.answer1);
        answer1TextView.setText(answer1);

        String answer2 = cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER1);
        TextView answer2TextView = (TextView) view.findViewById(R.id.answer2);
        answer2TextView.setText(answer2);

        String answer3 = cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER2);
        TextView answer3TextView = (TextView) view.findViewById(R.id.answer3);
        answer3TextView.setText(answer3);

        String answer4 = cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER3);
        TextView answer4TextView = (TextView) view.findViewById(R.id.answer4);
        answer4TextView.setText(answer4);
    }

}