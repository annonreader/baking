package com.example.taruntanmay.bakingapp.json;

import android.os.Parcel;
import android.os.Parcelable;

public class ingredients implements Parcelable {

    private String quantity;
    private String measure;
    private String ingredient;


    public ingredients(){}
    public ingredients(String quantity, String measure, String ingredient)
    {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    protected ingredients(Parcel in) {
        this.quantity = in.readString();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Parcelable.Creator<ingredients> CREATOR = new Parcelable.Creator<ingredients>() {
        @Override
        public ingredients createFromParcel(Parcel source) {
            return new ingredients(source);
        }

        @Override
        public ingredients[] newArray(int size) {
            return new ingredients[size];
        }
    };
}
