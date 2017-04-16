package com.gustavoballeste.capstone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    GridView grid;
    String[] web = {
            "General",
            "Books",
            "Film",
            "Music",
            "Theatre",
            "Television",
            "Video Games",
            "Board Games",
            "Nature",
            "Computers",
            "Mathematics",
            "Mythology",
            "Sports",
            "Geography",
            "History",
            "Politics",
            "Art",
            "Celebrities",
            "Animals",
            "Vehicles",
            "Comics",
            "Gadgets",
            "Anime",
            "Cartooon"
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomGrid adapter = new CustomGrid(MainActivity.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

    }

}