/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    private static final int mCount = 10;
    private List<WidgetItem> mWidgetItems = new ArrayList<WidgetItem>();
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
        for (int i = 0; i < getDescription().length; i++) {
            mWidgetItems.add(new WidgetItem(getDescription()[i], getImageId()[i]));
        }

        // We sleep for 3 seconds here to show how the empty view appears in the interim.
        // The empty view is set in the StackWidgetProvider and should be a sibling of the
        // collection view.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your data source,
        // eg. cursors, connections, etc.
        mWidgetItems.clear();
    }

    public int getCount() {
        return mCount;
    }

    public RemoteViews getViewAt(int position) {
        // position will always range from 0 to getCount() - 1.

        // We construct a remote views item based on our widget item xml file, and set the
        // description based on the position.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.widget_item_description, mWidgetItems.get(position).description);
        rv.setImageViewResource(R.id.widget_item_image, mWidgetItems.get(position).imageId);

        // Next, we set a fill-intent which will be used to fill-in the pending intent template
        // which is set on the collection view in StackWidgetProvider.
        Bundle extras = new Bundle();
        extras.putInt(StackWidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_item_description, fillInIntent);
        rv.setOnClickFillInIntent(R.id.widget_item_image, fillInIntent);

        // You can do heaving lifting in here, synchronously. For example, if you need to
        // process an image, fetch something from the network, etc., it is ok to do it here,
        // synchronously. A loading view will show up in lieu of the actual contents in the
        // interim.
        try {
            System.out.println("Loading view " + position);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return the remote views object.
        return rv;
    }

    public RemoteViews getLoadingView() {
        // You can create a custom loading view (for instance when getViewAt() is slow.) If you
        // return null here, you will get the default loading view.
        return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

    public void onDataSetChanged() {
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do heaving lifting in
        // here, synchronously. For example, if you need to process an image, fetch something
        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
        // in its current state while work is being done here, so you don't need to worry about
        // locking up the widget.
    }

    private String[] getDescription() {
        String[] description = {
                mContext.getResources().getString(R.string.category_general),
                mContext.getResources().getString(R.string.category_books),
                mContext.getResources().getString(R.string.category_film),
                mContext.getResources().getString(R.string.category_music),
                mContext.getResources().getString(R.string.category_theatres),
                mContext.getResources().getString(R.string.category_television),
                mContext.getResources().getString(R.string.category_video_games),
                mContext.getResources().getString(R.string.category_board_games),
                mContext.getResources().getString(R.string.category_nature),
                mContext.getResources().getString(R.string.category_computers),
                mContext.getResources().getString(R.string.category_mathematics),
                mContext.getResources().getString(R.string.category_mythology),
                mContext.getResources().getString(R.string.category_sports),
                mContext.getResources().getString(R.string.category_geography),
                mContext.getResources().getString(R.string.category_history),
                mContext.getResources().getString(R.string.category_politics),
                mContext.getResources().getString(R.string.category_art),
                mContext.getResources().getString(R.string.category_celebrities),
                mContext.getResources().getString(R.string.category_animals),
                mContext.getResources().getString(R.string.category_vehicles),
                mContext.getResources().getString(R.string.category_comics),
                mContext.getResources().getString(R.string.category_gadgets),
                mContext.getResources().getString(R.string.category_anime),
                mContext.getResources().getString(R.string.category_cartoon)
        };
        return description;
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