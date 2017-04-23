package com.gustavoballeste.capstone;

import android.annotation.TargetApi;
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
import android.widget.AdapterViewAnimator;
import android.widget.Button;
import android.widget.TextView;

import com.gustavoballeste.capstone.adapter.QuestionAdapter;
import com.gustavoballeste.capstone.data.QuestionContract;
import com.gustavoballeste.capstone.helper.ApiLevelHelper;
import com.gustavoballeste.capstone.helper.Utility;
import com.gustavoballeste.capstone.query.FetchQuestionTask;

/**
 * Created by gustavoballeste on 18/04/17.
 */

public class QuestionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    int mNum;
    private QuestionAdapter mAdapter;
    AdapterViewAnimator mQuestionView;

    private static final int QUESTION_LOADER = 0;

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

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());

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
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());

        mAdapter = new QuestionAdapter(getActivity(), null, 0);

        View view = inflater.inflate(R.layout.question_fragment, container, false);
        mQuestionView = (AdapterViewAnimator) view.findViewById(R.id.question_view_flipper);
        mQuestionView.setAdapter(mAdapter);
        mQuestionView.setSelection(0); //Definir aqui a posição do adapter que será apresentada
        setQuizViewAnimations();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //Estes objetos estão na list_item_question.xml
//        View countTv = view.findViewById(R.id.count);
//        ((TextView)countTv).setText(mNum+"/10");

        // Watch for button clicks.
//        Button button = (Button) view.findViewById(R.id.goto_next);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mQuestionView.showNext();
//            }
//        });

        super.onViewCreated(view, savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setQuizViewAnimations() {
        if (ApiLevelHelper.isLowerThan(Build.VERSION_CODES.LOLLIPOP)) {
            return;
        }
        mQuestionView.setInAnimation(getActivity(), R.animator.slide_in_bottom);
        mQuestionView.setOutAnimation(getActivity(), R.animator.slide_out_top);
    }

    private QuestionAdapter getQuizAdapter() {
        if (null == mAdapter) {
            mAdapter = new QuestionAdapter(getContext(), null, 0);
        }
        return mAdapter;
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

        moviesTask.execute(12);//passar a categoria

        getLoaderManager().restartLoader(QUESTION_LOADER, null, this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d("GUSTAVO DEBUG", new Object(){}.getClass().getEnclosingMethod().getName());
        mAdapter.swapCursor(null);
    }

}
