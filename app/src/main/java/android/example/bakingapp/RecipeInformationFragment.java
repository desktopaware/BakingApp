package android.example.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecipeInformationFragment extends Fragment {

    private RecyclerView recycler_view_ingredients;
    private RecyclerView recycler_view_steps;
    private List<Ingredients> ingredients;
    private List<Steps> steps;
    private IngredientsAdapter ingredientsAdapter;
    private StepsAdapter stepsAdapter;
    private Recipes recipes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_recipe_information, container, false);

        recycler_view_ingredients = view.findViewById(R.id.recycler_view_ingredients);
        recycler_view_steps = view.findViewById(R.id.recycler_view_steps);

        recipes = getArguments().getParcelable("recipe");

        ingredients = recipes.getIngredients();

        ingredientsAdapter = new IngredientsAdapter(ingredients);
        recycler_view_ingredients.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view_ingredients.setAdapter(ingredientsAdapter);
        ingredientsAdapter.notifyDataSetChanged();

        steps = recipes.getSteps();

        stepsAdapter = new StepsAdapter(steps);
        recycler_view_steps.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view_steps.setAdapter(stepsAdapter);
        stepsAdapter.notifyDataSetChanged();

        return view;

    }
}
