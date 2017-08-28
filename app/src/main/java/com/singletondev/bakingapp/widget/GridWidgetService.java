package com.singletondev.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.singletondev.bakingapp.R;

import java.util.List;

import static com.singletondev.bakingapp.widget.NewAppWidget.ingridientList;

/**
 * Created by Randy Arba on 8/28/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public class GridWidgetService extends RemoteViewsService {

    public List<String> remoteViewIngridients;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteFact(this.getApplicationContext(),intent);
    }

    class GridRemoteFact implements RemoteViewsService.RemoteViewsFactory {

        Context context = null;

        public GridRemoteFact(Context context,Intent intent){
            this.context = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            remoteViewIngridients = ingridientList;

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return remoteViewIngridients.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {

            RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            view.setTextViewText(R.id.widget_grid_view_item,remoteViewIngridients.get(i));

            Intent intent = new Intent();
            view.setOnClickFillInIntent(R.id.widget_grid_view_item,intent);
            return view;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
