package com.singletondev.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.singletondev.bakingapp.modelData.resep;

import java.util.ArrayList;

/**
 * Created by Randy Arba on 8/28/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public class updateBakingServ extends IntentService {

    public static String SELECTED_RECIPES = "Selected_Recipes";

    public static String FROM_ACTIVITY_INGREDIENTS_LIST ="FROM_ACTIVITY_INGREDIENTS_LIST";

    public updateBakingServ() {
        super("updateBakingServ");
    }

    public static void startService(Context context, ArrayList<String> fromActivity, ArrayList<resep> fromResep){

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SELECTED_RECIPES,fromResep);

        Intent intent = new Intent(context, updateBakingServ.class);
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivity);
        intent.putExtras(bundle);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
            ArrayList<String> fromActivity = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            ArrayList<resep> fromResep = intent.getExtras().getParcelableArrayList(SELECTED_RECIPES);

            Log.e("resepnya adalah",fromResep.get(0).getName());

            Bundle bundle2 = new Bundle();
            bundle2.putParcelableArrayList(SELECTED_RECIPES,fromResep);

            Intent intent1 = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
            intent1.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
            intent1.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivity);

            intent1.putExtras(bundle2);

            Log.e("Size4",String.valueOf(fromActivity.size()));
            sendBroadcast(intent1);
        }
    }
}
