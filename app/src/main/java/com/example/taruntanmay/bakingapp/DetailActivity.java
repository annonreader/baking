package com.example.taruntanmay.bakingapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.taruntanmay.bakingapp.json.ingredients;
import com.example.taruntanmay.bakingapp.json.recipe;
import com.google.gson.Gson;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{

    FrameLayout instructionContainer;
    Toolbar toolbar;

    private static final String RECIPE = "recipe";
    private static final int INITIALIZED_STEP_ID = 0;

    public static boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        Log.d("Detail activity","Why are you calling me agian");
        instructionContainer = findViewById(R.id.instruction_container);
//        toolbar = findViewById(R.id.detail_toolbar);
//        setSupportActionBar(toolbar);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        //Handle when activity is recreated like on orientation Change
        shouldDisplayHomeUp();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recipe recipe;
        InstructionFragment instructionFragment;
        if (findViewById(R.id.video_container) != null) {
            twoPane = true;
            if (savedInstanceState == null) {
                recipe = getIntent().getExtras().getParcelable(RECIPE);

                instructionFragment = new InstructionFragment();
                VideoFragment videoFragment = new VideoFragment();

                instructionFragment.setRecipe(recipe);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.instruction_container, instructionFragment)
                        .commit();

                videoFragment.setSteps(recipe.getSteps());
                videoFragment.setStepId(INITIALIZED_STEP_ID);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.video_container, videoFragment)
                        .commit();
            }
        } else {
            twoPane = false;
            if (savedInstanceState == null) {
                recipe = getIntent().getExtras().getParcelable(RECIPE);

                instructionFragment = new InstructionFragment();
                instructionFragment.setRecipe(recipe);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.instruction_container, instructionFragment)
                        .addToBackStack(null)
                        .commit();


                }
            }
        }


    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1 || twoPane) {
            finish();
        } else {

            getSupportFragmentManager().popBackStack();
        }
        //This method is called when the up button is pressed. Just the pop back stack.
       return true;
    }

}