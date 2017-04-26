package com.gustavoballeste.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.gustavoballeste.capstone.adapter.GridAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView grid = (GridView) findViewById(R.id.categories_grid_view);
        grid.setAdapter(new GridAdapter(this));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                TextView tv = (TextView) v.findViewById(R.id.category_description_text_view);
                String category = tv.getText().toString();

                Intent intent = new Intent(MainActivity.this, QuestionActivity.class)
                        .putExtra("category", category)
                        .putExtra("category_code", getCategoryId(position));
                startActivity(intent);
            }
        });
    }

    //Return the category ID according to opentdb.org
    private int getCategoryId(int position) {
        return position + 9; //9 is the inicial category code of opentdb.org
    }
}