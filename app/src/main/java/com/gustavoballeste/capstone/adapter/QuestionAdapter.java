package com.gustavoballeste.capstone.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gustavoballeste.capstone.QuestionFragment;
import com.gustavoballeste.capstone.R;
import com.gustavoballeste.capstone.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gustavoballeste on 18/04/17.
 */

public class QuestionAdapter extends CursorAdapter {

    private final Context mContext;

    public QuestionAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_question, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());

        //Estes objetos estão na list_item_question.xml

//        loadViewFromCursor();
        Question question = new Question(cursor);
        question.getQuestionNumber();

        String questionNumber = cursor.getString(QuestionFragment.COL_QUESTION_NUMBER);
        View countTv = view.findViewById(R.id.count);
        ((TextView)countTv).setText(questionNumber + "/10");


        setListener(view, R.id.answer1);
        setListener(view, R.id.answer2);

        String statement = question.getStatement();

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


    }

    private void loadViewFromCursor() {
    }

    private void setListener(View rootView, int resourceId) {

        final TextView textView = (TextView) rootView.findViewById(resourceId);
        final View view = rootView;
        textView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setTextViewBackgroundColor(textView, view);
            }
        });

    }

    private void setTextViewBackgroundColor(TextView textView, View rootView) {
        int textViewId = textView.getId();
        int answer1Id = R.id.answer1;
        int answer2Id = R.id.answer2;
//        int answer3Id = R.id.answer3;
//        int answer4Id = R.id.answer4;


        boolean isSelectedAnswer1 = (answer1Id == textViewId);
        boolean isSelectedAnswer2 = (answer2Id == textViewId);
//        boolean isSelectedanswer3 = (R.id.answer3 == textViewId);
//        boolean isSelectedanswer4 = (R.id.answer4 == textViewId);

        setAnswersBackgoundColor(answer1Id, isSelectedAnswer1, rootView);
        setAnswersBackgoundColor(answer2Id, isSelectedAnswer2, rootView);
//        setAnswersBackgoundColor(textView, answer3);
//        setAnswersBackgoundColor(textView, answer4);
    }

    private void setAnswersBackgoundColor(int id, boolean selected, View view) {

        TextView tv = (TextView)view.findViewById(id);
        if (selected) {
            tv.setBackgroundColor(ContextCompat.getColor(mContext, R.color.answer_selected_color));
        }
        else {
            tv.setBackgroundColor(ContextCompat.getColor(mContext, R.color.answer_unselected_color));
        }
    }


}