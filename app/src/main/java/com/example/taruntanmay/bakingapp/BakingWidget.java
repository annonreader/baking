package com.example.taruntanmay.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.taruntanmay.bakingapp.json.ingredients;
import com.example.taruntanmay.bakingapp.json.recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {
    private static final String PREFERENCE_NAME = "recipesPref";
    private static final String PREFERENCE_KEY = "recipes";
    private static final String PREFERENCE_ID = "recipeId";
    private static final String RECIPE_SIZE = "recipeSize";
    private static final String WIDGET_UPDATE_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";
    private static final String MyOnClick = "myOnClickTag";


    //receive broadcast to update recipe
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), WidgetService.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        updateRecipe(context);
        onUpdate(context, appWidgetManager, appWidgetIds);
    }
        /*if (MyOnClick.equals(intent.getAction())){//your onClick action is here
         */


    private void updateRecipe(Context context) {
        SharedPreferences SharedPrefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = SharedPrefs.edit();
        int recipeId = SharedPrefs.getInt(PREFERENCE_ID, 0);
        int recipesSize = SharedPrefs.getInt(RECIPE_SIZE, 1);

        if (recipeId < recipesSize - 1) {
            recipeId++;
        } else {
            recipeId = 0;
        }
        editor.putInt(PREFERENCE_ID, recipeId);
        editor.apply();
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
     //   Intent intent = new Intent(context, RemoteService.class);
  //      views.setRemoteAdapter(R.id.widgetListView, intent);


    }

  /*  protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }*/


    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

    //    List<recipe> rp = getIngredients(context);
    //    List<ingredients> ir = rp.get(getId(context)).getIngredients();

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        String title = getIngredientsTitle(context);
        views.setTextViewText(R.id.widget_title, title);

        Intent intent = new Intent(context, WidgetService.class);
        views.setRemoteAdapter(R.id.widget_list, intent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
  /*      int id = getId(context);
        List<recipe> rp = getIngredients(context);
        List<ingredients> ig= rp.get(id).getIngredients();
        views.setTextViewText(R.id.widget_detail,ig.get(0).getIngredient());*/

 /*       Intent intent = new Intent(context, RemoteService.class);
     //   intent.putExtra("id",)
        views.setRemoteAdapter(R.id.widget_list, intent);*/

        // broadcast to update widget
       Intent updateRecipeIntent = new Intent();
        updateRecipeIntent.setAction(WIDGET_UPDATE_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                updateRecipeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_title, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
    }

 /*   @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        ComponentName thisWidget = new ComponentName(context, BakingWidget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
            String title = getIngredientsTitle(context);
     //       int id = get(context);
       //     List<recipe> rr = ra(context);
       //     List<ingredients> ig = rr.get(id).getIngredients();
            views.setTextViewText(R.id.widget_title,title );

            Intent intent = new Intent(context, RemoteService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            views.setRemoteAdapter(R.id.widget_list, intent);

            // broadcast to update widget
            Intent intent1 = new Intent(context,BakingWidget.class);
            intent1.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    0,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_title, pendingIntent);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(widgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.widget_list);
        }
    }

*/


 /*   public List<recipe> ra(Context context)
    {
        SharedPreferences SharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int recipeId = SharedPrefs.getInt(RECIPE_ID_PREFERENCE_KEY, 0);
        String jsonString = SharedPrefs.getString(RECIPES_PREFERENCE_KEY, null);
        Type type = new TypeToken<List<recipe>>() {}.getType();
        Gson gson = new Gson();
        List<recipe> recipes = gson.fromJson(jsonString,type);
        return recipes;
    }
    public int get(Context context)
    {
        SharedPreferences SharedPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int recipeId = SharedPrefs.getInt(RECIPE_ID_PREFERENCE_KEY, 0);
        String jsonString = SharedPrefs.getString(RECIPES_PREFERENCE_KEY, null);
        Type type = new TypeToken<List<recipe>>() {}.getType();
        Gson gson = new Gson();
        return recipeId;

    }*/
    public String getIngredientsTitle(Context context) {
        SharedPreferences SharedPrefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        int recipeId = SharedPrefs.getInt(PREFERENCE_ID, 0);
        String jsonString = SharedPrefs.getString(PREFERENCE_KEY, null);
        Type type = new TypeToken<List<recipe>>() {}.getType();
        Gson gson = new Gson();
        List<recipe> recipes = gson.fromJson(jsonString , type);
     //   updateingredients(recipes.get(recipeId).getIngredients(),recipeId);
        return recipes.get(recipeId).getName();
    }


  /*  public List<recipe> getIngredients(Context context) {
        SharedPreferences SharedPrefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        int recipeId = SharedPrefs.getInt(PREFERENCE_ID, 0);
        String jsonString = SharedPrefs.getString(PREFERENCE_KEY, null);
        Type type = new TypeToken<List<recipe>>() {}.getType();
        Gson gson = new Gson();
        List<recipe> recipes = gson.fromJson(jsonString , type);
       // updateingredients(recipes.get(recipeId).getIngredients(),recipeId);
        return recipes;
    }

    public int getId(Context context) {
        SharedPreferences SharedPrefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        int recipeId = SharedPrefs.getInt(PREFERENCE_ID, 0);
        return recipeId;
    }*/

}

