package com.example.taruntanmay.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taruntanmay.bakingapp.DetailActivity;
import com.example.taruntanmay.bakingapp.R;
import com.example.taruntanmay.bakingapp.json.*;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class radapter extends RecyclerView.Adapter <radapter.ViewHolder> {
    private Context context;
    private List<recipe> recipes;
    private static final String RECIPE = "recipe";

    public radapter(Context context) {
        this.context = context;
    }

    @Override
    public radapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(radapter.ViewHolder holder, int position) {
        recipe currentRecipe = recipes.get(position);
        holder.name.setText(currentRecipe.getName());
        holder.servingNum.setText("Servings: "+currentRecipe.getServingNum());
        holder.recipe = currentRecipe;
        if (!TextUtils.isEmpty(currentRecipe.getImageUrl())){
            Picasso.with(context)
                    .load(currentRecipe.getImageUrl())
                    .placeholder(R.drawable.recipe_icon)
                    .error(R.drawable.recipe_icon)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipecover)
        ImageView imageView;
        @BindView(R.id.foodname)
        TextView name;
        @BindView(R.id.servings)
        TextView servingNum;

        recipe recipe;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
      //      imageView = itemView.findViewById(R.id.recipecover);
     //       name = itemView.findViewById(R.id.foodname);
       //    servingNum=itemView.findViewById(R.id.servings );
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(RECIPE,recipe);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void setRecipes(List<recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}
