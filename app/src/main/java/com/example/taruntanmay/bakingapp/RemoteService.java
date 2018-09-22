package com.example.taruntanmay.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.taruntanmay.bakingapp.json.ingredients;
import com.example.taruntanmay.bakingapp.json.recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RemoteService implements RemoteViewsService.RemoteViewsFactory {
  /*  private static final String[] items={"lorem", "ipsum", "dolor",
            "sit", "amet", "consectetuer",
            "adipiscing", "elit", "morbi",
            "vel", "ligula", "vitae",
            "arcu", "aliquet", "mollis",
            "etiam", "vel", "erat",
            "placerat", "ante",
            "porttitor", "sodales",
            "pellentesque", "augue",
            "purus"};*/

    private static final String PREFERENCE_NAME = "recipesPref";
    private static final String PREFERENCE_KEY = "recipes";
    private static final String PREFERENCE_ID= "recipeId";
    private Context ctxt;
    private List<ingredients> ingredients;
    private List<recipe> re;
    private recipe ra;
    private int appWidgetId;


    public RemoteService(Context ctxt,Intent intent) {
        this.ctxt=ctxt;
        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
               AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        // no-op
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        if (ingredients == null) return 0;
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (ingredients == null) return null;
        ingredients item = ingredients.get(position);


        RemoteViews row=new RemoteViews(ctxt.getPackageName(),
                R.layout.item_ingredient2);

    //    ra = getIngredientsFromPreferences(ctxt);
        Log.d("inside remote",item.getIngredient());
        row.setTextViewText(R.id.tv_ingredient_quantity, item.getQuantity());
        row.setTextViewText(R.id.tv_ingredient_unit, item.getMeasure());
        row.setTextViewText(R.id.tv_ingredient_description, item.getIngredient());

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(false);
    }

    public List<ingredients> getIngredientsFromPreferences(Context context) {
        SharedPreferences SharedPrefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        int Id = SharedPrefs.getInt(PREFERENCE_ID, 0);

        String jsonString = SharedPrefs.getString(PREFERENCE_KEY, null);
        Type type = new TypeToken<List<recipe>>() {
        }.getType();
        Gson gson = new Gson();
        List<recipe> recipes = gson.fromJson(jsonString, type);

        return recipes.get(Id).getIngredients();
    }
    @Override
    public void onDataSetChanged() {
        ingredients = getIngredientsFromPreferences(ctxt);

    }
}