package com.example.taruntanmay.bakingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taruntanmay.bakingapp.R;

import java.util.List;
import com.example.taruntanmay.bakingapp.json.*;

public class ingredient extends RecyclerView.Adapter<ingredient.ViewHolder> {
    private List<ingredients> ingredients;

    public ingredient(List<ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public ingredient.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootVIew = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_ingredients, parent, false);

        return new ViewHolder(rootVIew);
    }

    @Override
    public void onBindViewHolder(ingredient.ViewHolder holder, int position) {
        ingredients currentItem = ingredients.get(position);
        holder.quantity.setText(currentItem.getQuantity());
        holder.unit.setText(currentItem.getMeasure());
        holder.description.setText(currentItem.getIngredient());
    }

    @Override
    public int getItemCount() {
        if (ingredients == null) return 0;
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quantity;
        TextView unit;
        TextView description;


        public ViewHolder(View itemView) {
            super(itemView);
            quantity =itemView.findViewById(R.id.ingredientquantity);
            unit = itemView.findViewById(R.id.ingredientunit);
            description = itemView.findViewById(R.id.ingredientdescription);
        }
    }
}