package android.example.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class RecipeDetails extends AppCompatActivity {

    private Recipes recipes;

    List<Ingredients> ingredients;
    List<Steps> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipes = getIntent().getParcelableExtra("recipe");

        ingredients = recipes.getIngredients();
        steps = recipes.getSteps();

        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipes);

        RecipeInformationFragment recipeInformationFragment = new RecipeInformationFragment();
        recipeInformationFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.recipe_details, recipeInformationFragment)
                .commit();

    }
}
