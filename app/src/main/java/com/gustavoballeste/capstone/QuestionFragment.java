package com.gustavoballeste.capstone;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gustavoballeste.capstone.adapter.GridAdapter;
import com.gustavoballeste.capstone.data.QuestionContract;
import com.gustavoballeste.capstone.query.FetchQuestionTask;

/**
 * Created by gustavoballeste on 18/04/17.
 */

public class QuestionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    int mNum;
    private GridAdapter adapter;

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
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     */
    public static QuestionFragment newInstance(int num) {
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

        super.onActivityCreated(savedInstanceState);
//        getLoaderManager().initLoader(QUESTION_LOADER, null, null); //ESTA LINHA ESTÁ COM ERRO
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

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

        if (Utility.hasNetworkConnection(getActivity()))
        {
            updateData();
        }

        super.onStart();
    }

    public void updateData(){
        Log.d("MoviesFragment_Log_Gus", "updateData");


        FetchQuestionTask moviesTask = new FetchQuestionTask(getActivity(), this);

        moviesTask.execute(12);//passar a categoria

        getLoaderManager().restartLoader(QUESTION_LOADER, null, this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

//Implementar todos esses métodos
//public interface Callback {public void onItemSelected(Movie movie);}
//
//@Override
//public void onSaveInstanceState(Bundle outState) {}
//
//@Override
//public void onStart() {}

//
//public void loadFavorites(){}

