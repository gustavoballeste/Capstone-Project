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

    final int OPENTDB_INICIAL_VALUE = 9; //9 is the inicial category code of opentdb.org

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] description = {
                getResources().getString(R.string.category_general),
                getResources().getString(R.string.category_books),
                getResources().getString(R.string.category_film),
                getResources().getString(R.string.category_music),
                getResources().getString(R.string.category_theatres),
                getResources().getString(R.string.category_television),
                getResources().getString(R.string.category_video_games),
                getResources().getString(R.string.category_board_games),
                getResources().getString(R.string.category_nature),
                getResources().getString(R.string.category_computers),
                getResources().getString(R.string.category_mathematics),
                getResources().getString(R.string.category_mythology),
                getResources().getString(R.string.category_sports),
                getResources().getString(R.string.category_geography),
                getResources().getString(R.string.category_history),
                getResources().getString(R.string.category_politics),
                getResources().getString(R.string.category_art),
                getResources().getString(R.string.category_celebrities),
                getResources().getString(R.string.category_animals),
                getResources().getString(R.string.category_vehicles),
                getResources().getString(R.string.category_comics),
                getResources().getString(R.string.category_gadgets),
                getResources().getString(R.string.category_anime),
                getResources().getString(R.string.category_cartoon)
        } ;

        int[] imageId = {
                R.drawable.ic_category_general,
                R.drawable.ic_category_books,
                R.drawable.ic_category_film,
                R.drawable.ic_category_music,
                R.drawable.ic_category_theatre,
                R.drawable.ic_category_television,
                R.drawable.ic_category_video_game,
                R.drawable.ic_category_board_games,
                R.drawable.ic_category_nature,
                R.drawable.ic_category_computer,
                R.drawable.ic_category_mathematics,
                R.drawable.ic_category_mythology,
                R.drawable.ic_category_sports,
                R.drawable.ic_category_geography,
                R.drawable.ic_category_history,
                R.drawable.ic_category_politics,
                R.drawable.ic_category_art,
                R.drawable.ic_category_celebrity,
                R.drawable.ic_category_animal,
                R.drawable.ic_category_vehicle,
                R.drawable.ic_category_comics,
                R.drawable.ic_category_gadgets,
                R.drawable.ic_category_anime,
                R.drawable.ic_category_cartoon
        };

        GridView grid = (GridView) findViewById(R.id.categories_grid_view);
        GridAdapter gridAdapter = new GridAdapter(this, description, imageId);
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