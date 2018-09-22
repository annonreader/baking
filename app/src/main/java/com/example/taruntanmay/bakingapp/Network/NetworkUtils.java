package com.example.taruntanmay.bakingapp.Network;

import android.net.Uri;
import android.util.Log;

import com.example.taruntanmay.bakingapp.json.ingredients;
import com.example.taruntanmay.bakingapp.json.recipe;
import com.example.taruntanmay.bakingapp.json.steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

class NetworkUtils  {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public static List<recipe> fetchDataFromServer(String url) {
        URL baseurl = buildUrl(url);
        String jsonResponse = null;
        try {
            jsonResponse = makehttprequest(baseurl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseJson(jsonResponse);
    }

    public static URL buildUrl(String videourl)
    {

        Uri builturi = Uri.parse(videourl).buildUpon()
                .build();

        URL url = null;

        try {
            url = new URL(builturi.toString());
            Log.d(LOG_TAG,builturi.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return url;
    }



    //connecting to the internet

    public static String makehttprequest(URL url)throws IOException
    {
        String jsonresponse = null;
        if(url==null)
            return null;
        HttpURLConnection urlConnection = null;
        InputStream reader  = null;
        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200)
            {
                Log.v(LOG_TAG,""+urlConnection.getResponseCode());
                reader = urlConnection.getInputStream();
                jsonresponse = readFromStream(reader);
            }
            else
            {
                Log.e(LOG_TAG, "error while connecting" + urlConnection.getResponseCode());
            }
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "error while parsing json Response", e);
        }
        finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null)
                reader.close();
        }
        return jsonresponse;
    }

    //retrieving data from the internet

    private static String readFromStream(InputStream reader)throws IOException
    {
        StringBuilder output = new StringBuilder();
        if (reader != null) {
            InputStreamReader isr = new InputStreamReader(reader, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                output.append(line);
                line = br.readLine();
            }
        }
        return output.toString();
    }

    private static List<recipe> parseJson(String jsonresponse) {

        if (jsonresponse == null) return null;
        List<recipe> recipes = new ArrayList<>();
        List<ingredients> ingredients = null;
        List<steps> steps = null;

        try {
            JSONArray response = new JSONArray(jsonresponse);
            for (int i = 0; i < response.length(); i++) {
                JSONObject item = response.getJSONObject(i);

                String recipeId = item.optString("id");
                String recipeName = item.optString("name");
                String servingNum = item.optString("servings" );
                String recipeImageUrl = item.optString("image" );

                JSONArray ingredientJson;
                if (item.has("ingredients")) {
                    ingredients = new ArrayList<>();
                    ingredientJson = item.getJSONArray("ingredients");
                    for (int j = 0; j < ingredientJson.length(); j++) {
                        JSONObject ingredientObj = ingredientJson.getJSONObject(j);
                        String quantity = ingredientObj.optString("quantity");
                        String measure = ingredientObj.optString("measure");
                        String ingredient = ingredientObj.optString("ingredient");

                        ingredients.add(new ingredients(quantity, measure, ingredient));
                    }
                }

                JSONArray stepJson;
                steps = new ArrayList<>();
                if (item.has("steps")) {
                    stepJson = item.getJSONArray("steps");
                    for (int k = 0; k < stepJson.length(); k++) {
                        JSONObject stepObj = stepJson.getJSONObject(k);
                        String id = stepObj.optString("id");
                        String shortDescription = stepObj.optString("shortDescription");
                        String description = stepObj.optString("description");
                        String videoUrl = stepObj.optString("videoURL");
                        String thumbnailUrl = stepObj.optString("thumbnailURL");
                        steps.add(new steps(id, shortDescription, description, videoUrl, thumbnailUrl));
                    }
                }
                recipes.add(new recipe(recipeId, recipeName, servingNum, recipeImageUrl, ingredients, steps));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return recipes;
    }



}


