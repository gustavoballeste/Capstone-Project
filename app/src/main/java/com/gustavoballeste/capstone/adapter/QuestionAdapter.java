package com.gustavoballeste.capstone.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gustavoballeste.capstone.QuestionFragment;
import com.gustavoballeste.capstone.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        //Estes objetos estão na list_item_question.xml
        String questionNumber = cursor.getString(QuestionFragment.COL_QUESTION_NUMBER);
        View countTv = view.findViewById(R.id.count);
        ((TextView)countTv).setText(questionNumber + "/10");

        String statement = cursor.getString(QuestionFragment.COL_STATEMENT);
        TextView statementTextView = (TextView) view.findViewById(R.id.statement);
        statementTextView.setText(statement);

        List<String> answersList = new ArrayList<>();
        String type = cursor.getString(QuestionFragment.COL_TYPE);
        if (type.equals("boolean")) {
            answersList.add(cursor.getString(QuestionFragment.COL_CORRECT_ANSWER));
            answersList.add(cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER1));
            TextView answer1TextView = (TextView) view.findViewById(R.id.answer1);
            TextView answer2TextView = (TextView) view.findViewById(R.id.answer2);
            answer1TextView.setText(answersList.get(0));
            answer2TextView.setText(answersList.get(1));
            TextView answer3 = (TextView) view.findViewById(R.id.answer3);
            TextView answer4 = (TextView) view.findViewById(R.id.answer4);
            answer3.setVisibility(View.GONE);
            answer4.setVisibility(View.GONE);
        }
        else {
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

        // Watch for button clicks.
//        Button button = (Button) view.findViewById(R.id.goto_next);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mQuestionView.showNext();
//            }
//        });

    }

}