package com.singletondev.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.singletondev.bakingapp.R;
import com.singletondev.bakingapp.RecipeDetailACtivity;
import com.singletondev.bakingapp.modelData.resep;

import java.util.ArrayList;
import java.util.List;

import static com.singletondev.bakingapp.widget.updateBakingServ.FROM_ACTIVITY_INGREDIENTS_LIST;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {


    public static String REMOTEVIEW_INGREDIENT_LIST = "REMOTEVIEW_INGREDIENT_LIST";
    public static String REMOTEVIEW_BUNDLE = "REMOTEVIEW_BUNDLE";
    public static String SELECTED_RECIPES = "Selected_Recipes";

    public static List<String> ingridientList = new ArrayList<>();
    public static ArrayList<resep> fromResep = new ArrayList<>();


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SELECTED_RECIPES,fromResep);

        Intent intent = new Intent(context, RecipeDetailACtivity.class);
        intent.addCategory(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.widget_grid_view,pendingIntent);

        //lets set the grid
        Intent intent1 = new Intent(context, GridWidgetService.class );
        views.setRemoteAdapter(R.id.widget_grid_view,intent1);

//        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
    }

    public static void updateBakingService(Context context,AppWidgetManager appWidgetManager, int[] appWidgetId){
        for (Integer appwidgetId : appWidgetId){
            updateAppWidget(context,appWidgetManager,appwidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetId = appWidgetManager.getAppWidgetIds(new ComponentName(context,NewAppWidget.class));

        final String actions = intent.getAction();
//        Log.e("RecieveAction",actions + " dan " + intent.getExtras().toString() );

        if (actions.equals("android.appwidget.action.APPWIDGET_UPDATE2")){

            ingridientList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);

            fromResep = intent.getExtras().getParcelableArrayList(SELECTED_RECIPES);
            Log.e("resep",fromResep.get(0).getName());

//            Log.e("Check",String.valueOf(ingridientList.size()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_grid_view);

            NewAppWidget.updateBakingService(context,appWidgetManager,appWidgetId);
            super.onReceive(context,intent);
        }


    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

