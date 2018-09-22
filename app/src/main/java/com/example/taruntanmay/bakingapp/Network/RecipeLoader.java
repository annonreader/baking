package com.example.taruntanmay.bakingapp.Network;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.taruntanmay.bakingapp.json.*;

import java.util.List;

public class RecipeLoader extends AsyncTaskLoader<List<recipe>> {
    private static final String URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private List<recipe> cache;

    public RecipeLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (cache == null) {
            forceLoad();
        }
    }

    @Override
    public List<recipe> loadInBackground() {
        return NetworkUtils.fetchDataFromServer(URL);
    }

    @Override
    public void deliverResult(List<recipe> data) {
        super.deliverResult(data);
        cache = data;
    }
}