package com.gustavoballeste.capstone.querie;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.gustavoballeste.capstone.QuestionFragment;
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

        final String QUESTIONDB_RESULTS = "results";
        JSONArray questionsArray = questionsJson.getJSONArray((QUESTIONDB_RESULTS));

        final String question_category = "category";
        final String question_type = "type";
        final String question_difficulty = "difficulty";
        final String question_statement = "question";
        final String question_correct_answer = "correct_answer";
        final String question_incorrect_answers = "incorrect_answers"; //Um array de 3 strings

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
                String incorrect_answer1 = questionJSONObject.getString(question_incorrect_answers+"[1]");
                String incorrect_answer2 = questionJSONObject.getString(question_incorrect_answers+"[2]");
                String incorrect_answer3 = questionJSONObject.getString(question_incorrect_answers+"[3]");

                ContentValues contentValues = new ContentValues();

                contentValues.put(QuestionContract.QuestionEntry.COLUMN_CATEGORY, category);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_TYPE, type);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_DIFFICULTY, difficulty);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_STATEMENT, statement);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_CORRECT_ANSWER, correct_answer);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER1, incorrect_answer1);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER2, incorrect_answer2);
                contentValues.put(QuestionContract.QuestionEntry.COLUMN_INCORRECT_ANSWER3, incorrect_answer3);

                cVVector.add(contentValues);
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

        String sortOrder = objects[0].toString();
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String moviesJsonStr = null;

        try{

            final String baseUrl = "https://opentdb.com/api.php?amount=10";
            //Incluir aqui o parâmetro categoria

//            final String SORT_PARAM = "sort_by";
//            final String API_KEY = "api_key";

            Uri queryUri = Uri.parse(baseUrl).buildUpon()
                    .appendEncodedPath(sortOrder)
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
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {

            }
            moviesJsonStr = buffer.toString();
            Log.v("Result", moviesJsonStr);

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
