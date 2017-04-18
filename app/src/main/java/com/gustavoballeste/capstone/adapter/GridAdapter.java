package com.gustavoballeste.capstone.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gustavoballeste.capstone.R;

public class GridAdapter extends BaseAdapter{
    private Context mContext;

    public GridAdapter(Context c) {
        mContext = c;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return description.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_single,null);

        } else {
            grid = convertView;
        }
        TextView textView = (TextView) grid.findViewById(R.id.category_description_text_view);
        ImageView imageView = (ImageView)grid.findViewById(R.id.category_image_view);
        textView.setText(description[position]);
        imageView.setImageResource(imageId[position]);

        return grid;
    }

    String[] description = {
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

}