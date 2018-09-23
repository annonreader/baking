package com.example.taruntanmay.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.taruntanmay.bakingapp.Adapters.ingredient;
import com.example.taruntanmay.bakingapp.Adapters.stepadapter;
import com.example.taruntanmay.bakingapp.json.ingredients;
import com.example.taruntanmay.bakingapp.json.steps;
import com.example.taruntanmay.bakingapp.json.recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.LinearLayout.HORIZONTAL;
import static java.security.AccessController.getContext;

public class InstructionFragment extends Fragment {
    @BindView(R.id.recycleringredients)
    RecyclerView ingredientsRv;
    @BindView(R.id.recyclersteps)
    RecyclerView stepsRv;
    DividerItemDecoration mDividerItemDecoration;
    LinearLayoutManager mLayoutManager;

    private static final String RECIPE = "recipe";
    private recipe recipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);
        ((DetailActivity) getActivity()).getSupportActionBar().show();
        ButterKnife.bind(this,rootView);
      //  ingredientsRv = (RecyclerView)rootView.findViewById(R.id.recycleringredients);
    //    stepsRv = (RecyclerView)rootView.findViewById(R.id.recyclersteps);
        mDividerItemDecoration = new DividerItemDecoration(ingredientsRv.getContext(),
                HORIZONTAL);
        ingredientsRv.addItemDecoration(mDividerItemDecoration);

        if (recipe != null) {
            ingredient adapter = new ingredient(recipe.getIngredients());
            ingredientsRv.setLayoutManager(new LinearLayoutManager(getContext()));
            ingredientsRv.setAdapter(adapter);
            stepadapter sadapter = new stepadapter(recipe.getSteps());
            stepsRv.setLayoutManager(new LinearLayoutManager(getContext()));
            stepsRv.setAdapter(sadapter);
        }
        return rootView;
    }

    public void onActivityCreated (Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                      //  Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);

                        return true;
                    }
                }
                return false;
            }
        });
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE);
            ingredient adapter = new ingredient(recipe.getIngredients());
            ingredientsRv.setLayoutManager(new LinearLayoutManager(getContext()));
            ingredientsRv.setAdapter(adapter);
            stepadapter sadapter = new stepadapter(recipe.getSteps());
            stepsRv.setLayoutManager(new LinearLayoutManager(getContext()));
            stepsRv.setAdapter(sadapter);
        }
    }

    public void setRecipe(recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE,recipe);
    }
}
