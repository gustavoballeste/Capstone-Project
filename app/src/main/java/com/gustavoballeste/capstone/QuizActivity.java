package com.gustavoballeste.capstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Bundle extra = getIntent().getExtras();
        int position = extra.getInt("position");
        TextView textView = (TextView)findViewById(R.id.position_text_view);
        textView.setText("You open the position "+position+" of the Grid.");
    }
}
