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

    public String mCategory;
    String[] description;
    int[] imageId;

    public GridAdapter(Context c, String[] d, int[] i) {
        mContext = c;
        description = d;
        imageId = i;

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
        mCategory = description[position];
        textView.setText(mCategory);

        ImageView imageView = (ImageView)grid.findViewById(R.id.category_image_view);
        imageView.setImageResource(imageId[position]);
        imageView.setContentDescription(mCategory);
        return grid;
    }
}