package com.example.taruntanmay.bakingapp.json;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class recipe implements Parcelable {

    private String id;
    private String name;
    private String servingNum;
    private String imageUrl;
    private List<ingredients> ingredients;
    private List<steps> steps;

    public recipe(String id, String name, String servingNum, String imageUrl,
                  List<ingredients> ingredients, List<steps> steps) {
        this.id = id;
        this.name = name;
        this.servingNum = servingNum;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServingNum() {
        return servingNum;
    }

    public void setServingNum(String servingNum) {
        this.servingNum = servingNum;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<com.example.taruntanmay.bakingapp.json.ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<com.example.taruntanmay.bakingapp.json.ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<com.example.taruntanmay.bakingapp.json.steps> getSteps() {
        return steps;
    }

    public void setSteps(List<com.example.taruntanmay.bakingapp.json.steps> steps) {
        this.steps = steps;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.servingNum);
        dest.writeString(this.imageUrl);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
    }

    protected recipe(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.servingNum = in.readString();
        this.imageUrl = in.readString();
        this.ingredients = new ArrayList<>();
        in.readList(this.ingredients, ingredients.class.getClassLoader());
        this.steps = new ArrayList<>();
        in.readList(this.steps, steps.class.getClassLoader());
    }

    public static final Parcelable.Creator<recipe> CREATOR = new Parcelable.Creator<recipe>() {
        @Override
        public recipe createFromParcel(Parcel source) {
            return new recipe(source);
        }

        @Override
        public recipe[] newArray(int size) {
            return new recipe[size];
        }
    };
}

