package android.example.bakingapp;

import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

public class RecipeDetails extends AppCompatActivity {

    private Recipes recipes;
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent() != null)
            recipes = getIntent().getParcelableExtra("recipe");

        FragmentManager fragmentManager = getSupportFragmentManager();


        if(findViewById(R.id.twoPane) != null || findViewById(R.id.twoPane_portrait) != null){

            mTwoPane = true;

            Bundle bundleOne = new Bundle();
            bundleOne.putInt("index", 0);
            bundleOne.putBoolean("mTwoPane", mTwoPane);
            bundleOne.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) recipes.getSteps());

            StepsInformationFragment stepsInformationFragment = new StepsInformationFragment();
            stepsInformationFragment.setArguments(bundleOne);

            fragmentManager.beginTransaction()
                    .add(R.id.steps_details, stepsInformationFragment)
                    .commit();

        }else {
            mTwoPane = false;
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipes);
        bundle.putBoolean("mTwoPane", mTwoPane);

        RecipeInformationFragment recipeInformationFragment =
                (RecipeInformationFragment) fragmentManager.findFragmentByTag("RecipeInformationFragment");

        if(recipeInformationFragment == null) {

            recipeInformationFragment = new RecipeInformationFragment();
            recipeInformationFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_details, recipeInformationFragment,"RecipeInformationFragment")
                    .commit();
        }

    }
}
