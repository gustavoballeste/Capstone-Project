package com.gustavoballeste.capstone;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
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
    private int mCategoryCode;
    private int mRoundScore = 0;
    private String mCategoryName;
    private static Cursor mCursor;

    private QuestionAdapter mAdapter;
    private AdapterViewFlipper mQuestionView;

    InterstitialAd mInterstitialAd;
    Button mNextButton;

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

    private static FirebaseAnalytics mFirebaseAnalytics;
    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mCategoryCode = getActivity().getIntent().getExtras().getInt(getString(R.string.category_code));
        mCategoryName = getActivity().getIntent().getExtras().getString(getString(R.string.category));

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Integer.toString(mCategoryCode));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, mCategoryName);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, getResources().getString(R.string.category));
        mFirebaseAnalytics.logEvent(getString(R.string.category_selected), bundle);

    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAdapter = new QuestionAdapter(getActivity(), null, 0);

        View view = inflater.inflate(R.layout.question_fragment, container, false);
        mQuestionView = (AdapterViewFlipper) view.findViewById(R.id.question_view_flipper);

        mQuestionView.setAdapter(mAdapter);
        mQuestionView.setSelection(1);
        setQuizViewAnimations();

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        mView = view;

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mNextButton = (Button) view.findViewById(R.id.goto_next);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                showTotalScore();
            }
        });

        requestNewInterstitial();

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNextButton.setVisibility(View.GONE);
                validateQuestion();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void requestNewInterstitial() {

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(getString(R.string.ad_test_device))
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public void validateQuestion() {

        int nextItem = mQuestionView.getDisplayedChild() + 1;
        final int count = mQuestionView.getAdapter().getCount();
        if (QuestionAdapter.mLastAnswerSelected.equals(new Question(mCursor).getCorrectAnswer())) {
            ScoreDBHelper.updateScore(getContext());
            mRoundScore++;
        }

        if (nextItem < count) {
            mQuestionView.showNext();
        }
        else {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                showTotalScore();
            }
        }

    }

    private void showTotalScore() {

        Intent intent = new Intent(getActivity(), ScoreActivity.class)
                .putExtra(getString(R.string.category), mCategoryName)
                .putExtra(getString(R.string.round_score), mRoundScore + getString(R.string.max_round_score))
                .putExtra(getString(R.string.category_code), mCategoryCode);
        startActivity(intent);
        getActivity().finish();

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

        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(QUESTION_LOADER, null, this);
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

        FetchQuestionTask moviesTask = new FetchQuestionTask(getActivity(), this);
        moviesTask.execute(mCategoryCode);
        getLoaderManager().restartLoader(QUESTION_LOADER, null, this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mCursor = data;
        mAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mAdapter.swapCursor(null);
    }

}
