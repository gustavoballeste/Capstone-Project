
package com.gustavoballeste.capstone.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.gustavoballeste.capstone.R;

import java.util.ArrayList;
import java.util.List;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<WidgetItem> mWidgetItems = new ArrayList<WidgetItem>();
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        String[] descriptions = mContext.getResources().getStringArray(R.array.categories);
        for (int i = 0; i < descriptions.length; i++) {
            mWidgetItems.add(new WidgetItem(descriptions[i], getImageId()[i]));
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        mWidgetItems.clear();
    }

    public int getCount() {
        return mWidgetItems.size();
    }

    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.widget_item_description, mWidgetItems.get(position).description);
        rv.setImageViewResource(R.id.widget_item_image, mWidgetItems.get(position).imageId);

        Bundle extras = new Bundle();
        extras.putInt(StackWidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_item_description, fillInIntent);
        rv.setOnClickFillInIntent(R.id.widget_item_image, fillInIntent);

        return rv;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return false;
    }

    public void onDataSetChanged() {
    }

    private int[] getImageId(){
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
        return imageId;
    }

}