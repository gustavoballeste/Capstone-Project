package com.gustavoballeste.capstone;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.Button;

import com.gustavoballeste.capstone.adapter.QuestionAdapter;
import com.gustavoballeste.capstone.data.QuestionContract;
import com.gustavoballeste.capstone.data.ScoreDBHelper;
import com.gustavoballeste.capstone.helper.ApiLevelHelper;
import com.gustavoballeste.capstone.helper.Utility;
import com.gustavoballeste.capstone.model.Question;
import com.gustavoballeste.capstone.query.FetchQuestionTask;


/**
 * Created by gustavoballeste on 18/04/17.
 */

public class QuestionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static View mView;
    int mNum;
    int mCategoryCode;
    private static Cursor mCursor;

    private QuestionAdapter mAdapter;
    private AdapterViewFlipper mQuestionView;

    private static final int QUESTION_LOADER = 0;

    public static final String[] QUESTION_COLUMNS = {
            QuestionContract.QuestionEntry._ID,
            QuestionContract.QuestionEntry.COLUMN_QUESTION_NUMBER,
            QuestionContract.QuestionEntry.COLUMN_STATEMENT,
            QuestionContract.QuestionEntry.COLUMN_CATEGORY,
            QuestionContract.QuestionEntry.COLUMN_TYPE,
            QuestionContract.QuestionEntry.COLUMN_DIFFICULTY,
            QuestionContract.QuestionEntry.COLUMN_CORRECT_ANSWER,
            QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER1,
            QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER2,
            QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER3,
            QuestionContract.QuestionEntry.COLUMN_RESULT,
    };

    public static final int COL_ID = 0;
    public static final int COL_QUESTION_NUMBER = 1;
    public static final int COL_STATEMENT = 2;
    public static final int COL_CATEGORY = 3;
    public static final int COL_TYPE = 4;
    public static final int COL_DIFFICULTY = 5;
    public static final int COL_CORRECT_ANSWER = 6;
    public static final int COL_INCORRECT_ANSWER1 = 7;
    public static final int COL_INCORRECT_ANSWER2 = 8;
    public static final int COL_INCORRECT_ANSWER3 = 9;
    public static final int COL_RESULT = 10;

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());

        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num")+1 : 1;
        mCategoryCode = getActivity().getIntent().getExtras().getInt("category_code");

    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());
        mAdapter = new QuestionAdapter(getActivity(), null, 0);

        View view = inflater.inflate(R.layout.question_fragment, container, false);
        mQuestionView = (AdapterViewFlipper) view.findViewById(R.id.question_view_flipper);

        mQuestionView.setAdapter(mAdapter);
        mQuestionView.setSelection(1); //Definir aqui a posição do adapter que será apresentada
        setQuizViewAnimations();

        //Debug Gustavo
        final int count = mQuestionView.getAdapter().getCount();
        Log.d("LOG_GUSTAVO Count", count+" registros no adapter");

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        mView = view;
        //Watch for button click to next question.
        final Button button = (Button) view.findViewById(R.id.goto_next);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button.setVisibility(View.GONE);
                validateQuestion();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void validateQuestion() {
        int nextItem = mQuestionView.getDisplayedChild() + 1;
        final int count = mQuestionView.getAdapter().getCount();
        if (QuestionAdapter.mLastAnswerSelected.equals(new Question(mCursor).getCorrectAnswer())) {
            Log.d("GUSTAVO", "Resposta correta");
            ScoreDBHelper.updateScore(getContext());
            //1. Altera a cor do textview para verde
        }
        else {
            //2. Altera a cor do textview para vermelho
            Log.d("GUSTAVO", "Resposta incorreta");
        }
        //3. Se o AdapterViewFlipper chegou no último registro, passsa para a tela de resposta,
        //      senão mostra a próxima pergunta.

        //4. Incluir um delay antes de passar ao próximo passo.
        if (nextItem < count) {
            Log.d("GUSTAVO", "Avança para a próxima pergunta");
            mQuestionView.showNext();
        }
        else {
            Intent intent = new Intent(getActivity(), ScoreActivity.class);
            startActivity(intent);
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setQuizViewAnimations() {
        if (ApiLevelHelper.isLowerThan(Build.VERSION_CODES.LOLLIPOP)) {
            return;
        }
        mQuestionView.setInAnimation(getActivity(), R.animator.slide_in_bottom);
        mQuestionView.setOutAnimation(getActivity(), R.animator.slide_out_top);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());

        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(QUESTION_LOADER, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());

        Loader<Cursor> cursorLoader;

        cursorLoader = new CursorLoader(getActivity(),
                QuestionContract.QuestionEntry.CONTENT_URI,
                QUESTION_COLUMNS,
                null,
                null,
                null);
        return cursorLoader;
    }

    @Override
    public void onStart() {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());

        if (Utility.hasNetworkConnection(getActivity()))
        {
            updateData();
        }

        super.onStart();
    }

    public void updateData(){
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());

        FetchQuestionTask moviesTask = new FetchQuestionTask(getActivity(), this);

        moviesTask.execute(mCategoryCode);

        getLoaderManager().restartLoader(QUESTION_LOADER, null, this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());

        mCursor = data;
        final int count1 = mQuestionView.getAdapter().getCount();
        Log.d(new Object(){}.getClass().getEnclosingMethod().getName(), count1+" registros no adapter");

        mAdapter.swapCursor(data);

        final int count2 = mQuestionView.getAdapter().getCount();
        Log.d(new Object(){}.getClass().getEnclosingMethod().getName(), count2+" registros no adapter");

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());
        mAdapter.swapCursor(null);
    }

}
