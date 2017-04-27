package com.gustavoballeste.capstone.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.gustavoballeste.capstone.QuestionFragment;
import com.gustavoballeste.capstone.R;
import com.gustavoballeste.capstone.model.Question;
import com.gustavoballeste.capstone.model.Score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by gustavoballeste on 18/04/17.
 */

public class QuestionAdapter extends CursorAdapter {

    private final Context mContext;

    TextView mCountView;
    TextView mStatementView;
    TextView mAnswer1View;
    TextView mAnswer2View;
    TextView mAnswer3View;
    TextView mAnswer4View;
    String mType;

    public static String mLastAnswerSelected;

    public QuestionAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item_question, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        Question question = new Question(cursor);
        String count = question.getQuestionNumber();
        String statement = question.getStatement();

        mCountView = (TextView) view.findViewById(R.id.count);
        mCountView.setText(count);
        mCountView.setContentDescription(count);


        mStatementView = (TextView) view.findViewById(R.id.statement);
        mStatementView.setText(statement);
        mStatementView.setContentDescription(mContext.getString(R.string.question_inicial_description) + statement);

        mType = question.getType();
        if (mType.equals(mContext.getString(R.string.boolean_type))) {
            loadBooleanTypeView(view);
        }
        else {
            loadMultipleTypeView(cursor, view);
        }
    }

    private void loadBooleanTypeView(View rootView) {

        mAnswer1View = (TextView) rootView.findViewById(R.id.answer1);
        mAnswer1View.setText(R.string.true_answer);
        mAnswer1View.setContentDescription(mContext.getResources().getString(R.string.true_answer));

        mAnswer2View = (TextView) rootView.findViewById(R.id.answer2);
        mAnswer2View.setText(R.string.false_answer);
        mAnswer1View.setContentDescription(mContext.getResources().getString(R.string.false_answer));

        setListener(rootView, R.id.answer1);
        setListener(rootView, R.id.answer2);

        //Hide the cardViews used just for type 'multiple'
        CardView cardView3 = (CardView)rootView.findViewById(R.id.answer3_card_view);
        cardView3.setVisibility(View.GONE);

        CardView cardView4 = (CardView)rootView.findViewById(R.id.answer4_card_view);
        cardView4.setVisibility(View.GONE);
    }

    private void loadMultipleTypeView(Cursor cursor, View rootView) {

        List<String> multipleAnswers = new ArrayList<>();
        multipleAnswers.add(cursor.getString(QuestionFragment.COL_CORRECT_ANSWER));
        multipleAnswers.add(cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER1));
        multipleAnswers.add(cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER2));
        multipleAnswers.add(cursor.getString(QuestionFragment.COL_INCORRECT_ANSWER3));
        Collections.shuffle(multipleAnswers);

        String answer1 = multipleAnswers.get(0);
        String answer2 = multipleAnswers.get(1);
        String answer3 = multipleAnswers.get(2);
        String answer4 = multipleAnswers.get(3);

        mAnswer1View = (TextView) rootView.findViewById(R.id.answer1);
        mAnswer1View.setText(answer1);
        mAnswer1View.setContentDescription(mContext.getString(R.string.option_1_inicial_description) + answer1);

        mAnswer2View = (TextView) rootView.findViewById(R.id.answer2);
        mAnswer2View.setText(answer2);
        mAnswer2View.setContentDescription(mContext.getString(R.string.option_2_inicial_description) + answer2);

        mAnswer3View = (TextView) rootView.findViewById(R.id.answer3);
        mAnswer3View.setText(answer3);
        mAnswer3View.setContentDescription(mContext.getString(R.string.option_3_inicial_description) + answer3);

        mAnswer4View = (TextView) rootView.findViewById(R.id.answer4);
        mAnswer4View.setText(answer4);
        mAnswer4View.setContentDescription(mContext.getString(R.string.option_4_inicial_description) + answer4);

        setListener(rootView, R.id.answer1);
        setListener(rootView, R.id.answer2);
        setListener(rootView, R.id.answer3);
        setListener(rootView, R.id.answer4);
    }

    private void setListener(final View rootView, int resourceId) {

        final TextView textView = (TextView) rootView.findViewById(resourceId);
        final View view = rootView;
        final View buttonRootView = QuestionFragment.mView;

        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button button = (Button)buttonRootView.findViewById(R.id.goto_next);
                button.setVisibility(View.VISIBLE);
                setTextViewBackgroundColor(textView, view);
                mLastAnswerSelected = (String)textView.getText();
            }
        });
    }

    private void setTextViewBackgroundColor(TextView textView, View rootView) {

        int textViewId = textView.getId();
        int answer1Id = R.id.answer1;
        int answer2Id = R.id.answer2;

        boolean isSelectedAnswer1 = (answer1Id == textViewId);
        boolean isSelectedAnswer2 = (answer2Id == textViewId);

        setAnswersBackgoundColor(answer1Id, isSelectedAnswer1, rootView);
        setAnswersBackgoundColor(answer2Id, isSelectedAnswer2, rootView);

        if (mType.equals(mContext.getString(R.string.multiple_type))) {
            int answer3Id = R.id.answer3;
            int answer4Id = R.id.answer4;
            boolean isSelectedAnswer3 = (answer3Id == textViewId);
            boolean isSelectedAnswer4 = (answer4Id == textViewId);
            setAnswersBackgoundColor(answer3Id, isSelectedAnswer3, rootView);
            setAnswersBackgoundColor(answer4Id, isSelectedAnswer4, rootView);
        }
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
