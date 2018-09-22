package com.example.taruntanmay.bakingapp;

import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.List;


import com.example.taruntanmay.bakingapp.Adapters.radapter;
import com.example.taruntanmay.bakingapp.Network.RecipeLoader;
import com.example.taruntanmay.bakingapp.json.recipe;
import com.google.gson.Gson;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<recipe>> {

    LinearLayout linearLayout;
  //  Toolbar toolbar;
    RecyclerView recyclerView;

    private static final String PREFERENCE_NAME = "recipesPref";
    private static final String PREFERENCE_KEY = "recipes";
    private static final String PREFERENCE_ID = "recipeId";
    private static final String RECIPE_SIZE = "recipeSize";
    private Toast mtoast;
    private radapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.layout_main);
        recyclerView = findViewById(R.id.recyclerecipe);

        if (isConnected()) {
            getSupportLoaderManager().initLoader(0, null, this);
        } else {
            Toast.makeText(getApplicationContext(), "Please Check Internet Connection ", Toast.LENGTH_SHORT);
        }
        mRecipeAdapter = new radapter(this);
        recyclerView.setAdapter(mRecipeAdapter);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {


            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }
    }

    private void saveData(List<recipe> data) {
        SharedPreferences prefs = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Log.d("ingredient",data.get(0).getName());
        Log.d("ingredient detail",data.get(0).getIngredients().get(0).getIngredient());
        Gson gson = new Gson();
        String serializedRecipes = gson.toJson(data);
        editor.putString(PREFERENCE_KEY, serializedRecipes);
        editor.putInt(PREFERENCE_ID, 0);
        editor.putInt(RECIPE_SIZE, data.size());
        editor.apply();
    }
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    public android.support.v4.content.Loader<List<recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipeLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<List<recipe>> loader, List<recipe> recipes) {
        if (recipes != null) {
            mRecipeAdapter.setRecipes(recipes);
                saveData(recipes);
        } else {
            Toast.makeText(getApplicationContext(),"No Recipes",Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<List<recipe>> loader) {
        mRecipeAdapter.setRecipes(null);

    }


}
