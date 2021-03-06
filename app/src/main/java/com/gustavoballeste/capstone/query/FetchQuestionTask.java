package com.gustavoballeste.capstone.query;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.gustavoballeste.capstone.QuestionFragment;
import com.gustavoballeste.capstone.R;
import com.gustavoballeste.capstone.data.QuestionContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

/**
 * Created by gustavoballeste on 18/04/17.
 */

public class FetchQuestionTask  extends AsyncTask {

    final String LOG_TAG = QuestionFragment.class.getSimpleName();

    private final Context mContext;
    private final QuestionFragment fragment;

    public FetchQuestionTask(Context mContext, QuestionFragment fragment) {
        this.mContext = mContext;
        this.fragment = fragment;
    }

    public void createMovieListFromJson(String questionsJsonString) throws JSONException {

        JSONObject questionsJson = new JSONObject(questionsJsonString);

        final String QUESTIONDB_RESULTS = mContext.getString(R.string.results);
        JSONArray questionsArray = questionsJson.getJSONArray((QUESTIONDB_RESULTS));
        final String question_category = mContext.getString(R.string.category);
        final String question_type = mContext.getString(R.string.type);
        final String question_difficulty = mContext.getString(R.string.difficulty);
        final String question_statement = mContext.getString(R.string.question);
        final String question_correct_answer = mContext.getString(R.string.correct_answer);
        final String question_incorrect_answers = mContext.getString(R.string.incorrect_answers);

        try {
            int deleted = mContext.getContentResolver().delete(QuestionContract.QuestionEntry.CONTENT_URI, null, null);
            Log.d(LOG_TAG, "Previous data wiping Complete. " + deleted + " Deleted");

            Vector<ContentValues> cVVector = new Vector<ContentValues>(questionsArray.length());

            for (int i = 0; i < questionsArray.length(); ++i) {
                JSONObject questionJSONObject = questionsArray.getJSONObject(i);
                String category = questionJSONObject.getString(question_category);
                String type = questionJSONObject.getString(question_type);
                String difficulty = questionJSONObject.getString(question_difficulty);
                String statement = questionJSONObject.getString(question_statement);
                String correct_answer = questionJSONObject.getString(question_correct_answer);
                String incorrect_answer1 = null;
                String incorrect_answer2 = null;
                String incorrect_answer3 = null;

                JSONArray incorrectQuestionsArray = questionJSONObject.getJSONArray((question_incorrect_answers));


                incorrect_answer1 = incorrectQuestionsArray.get(0).toString();

                if (type.equals(mContext.getString(R.string.multiple))) {
                    incorrect_answer2 = incorrectQuestionsArray.get(1).toString();
                    incorrect_answer3 = incorrectQuestionsArray.get(2).toString();
                }

                ContentValues contentValues = new ContentValues();

                contentValues.put(QuestionContract.QuestionEntry.COLUMN_CATEGORY, category);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_QUESTION_NUMBER, (i+1+mContext.getString(R.string.max_question_number_column)));
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_TYPE, type);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_DIFFICULTY, difficulty);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_STATEMENT, statement);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_CORRECT_ANSWER, correct_answer);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER1, incorrect_answer1);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER2, incorrect_answer2);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER3, incorrect_answer3);

                cVVector.add(contentValues);

                int questionNumber = i+1;
                Log.d("Content Values", "category:" + category + " | question_number:" + questionNumber + " | type:" + type + " | difficulty:" + difficulty + " | statement:" + statement + " | correct_answer:" + correct_answer + " | incorrect_answer1:" + incorrect_answer1 + " | incorrect_answer2:" + incorrect_answer2 + " | incorrect_answer2:" + incorrect_answer2);

            }
            int inserted = 0;

            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                inserted = mContext.getContentResolver().bulkInsert(QuestionContract.QuestionEntry.CONTENT_URI, cvArray);
            }
            Log.d(LOG_TAG, "FetchQuestionsTask complete. " + inserted + " inserted");
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Object[] objects) {

        String categoryCode = objects[0].toString();

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String moviesJsonStr;

        try{

            final String baseUrl = mContext.getString(R.string.base_opentdb_url);

           final String SORT_PARAM = mContext.getString(R.string.category);

            Uri queryUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter(SORT_PARAM, categoryCode)
                    .build();

            URL url = new URL(queryUri.toString());

            Log.v(LOG_TAG, "THE URL IS: " + url);

            // Create the request to Open Trivia Database, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {

            }
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {

            }
            moviesJsonStr = buffer.toString();
            Log.v("Score", moviesJsonStr);

            createMovieListFromJson(moviesJsonStr);


        }catch (IOException e) {
            Log.e("FetchQuestionTask", "Error ", e);

            return null;
        }
        catch (JSONException e){
            Log.e("FetchQuestionTask", "Error parsing JSON ", e);
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("FetchQuestionTask", "Error closing stream", e);
                }
            }
        }
        return null;
    }

}
