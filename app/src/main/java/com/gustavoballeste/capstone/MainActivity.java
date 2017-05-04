package com.gustavoballeste.capstone;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.gustavoballeste.capstone.adapter.GridAdapter;

public class MainActivity extends AppCompatActivity {

    final int OPENTDB_INICIAL_VALUE = 9; //9 is the inicial category code of opentdb.org

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(myToolbar);

        String[] descriptions = getResources().getStringArray(R.array.categories);
        TypedArray images = getResources().obtainTypedArray(R.array.images);

        GridView grid = (GridView) findViewById(R.id.categories_grid_view);
        GridAdapter gridAdapter = new GridAdapter(this, descriptions, images);
        grid.setAdapter(gridAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                TextView tv = (TextView) v.findViewById(R.id.category_description_text_view);
                String category = tv.getText().toString();

                Intent intent = new Intent(MainActivity.this, QuestionActivity.class)
                        .putExtra(getString(R.string.category), category)
                        .putExtra(getString(R.string.category_code), getCategoryId(position));
                startActivity(intent);
            }
        });
    }

    //Return the category ID according to opentdb.org
    private int getCategoryId(int position) {
        return position + OPENTDB_INICIAL_VALUE;
    }

}