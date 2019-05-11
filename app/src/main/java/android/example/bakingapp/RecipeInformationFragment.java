package android.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RecipeInformationFragment extends Fragment implements StepsAdapter.OnStepListener{

    private RecyclerView recycler_view_ingredients;
    private RecyclerView recycler_view_steps;
    private List<Ingredients> ingredients;
    private List<Steps> steps;
    private IngredientsAdapter ingredientsAdapter;
    private StepsAdapter stepsAdapter;
    private Recipes recipes;
    private Button button;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_recipe_information, container, false);

        recycler_view_ingredients = view.findViewById(R.id.recycler_view_ingredients);
        recycler_view_steps = view.findViewById(R.id.recycler_view_steps);
        button = view.findViewById(R.id.widget_button);

        recipes = getArguments().getParcelable("recipe");

        ingredients = recipes.getIngredients();

        ingredientsAdapter = new IngredientsAdapter(ingredients);
        recycler_view_ingredients.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view_ingredients.setAdapter(ingredientsAdapter);
        ingredientsAdapter.notifyDataSetChanged();

        steps = recipes.getSteps();

        stepsAdapter = new StepsAdapter(context, steps, this);
        recycler_view_steps.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view_steps.setAdapter(stepsAdapter);
        stepsAdapter.notifyDataSetChanged();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", getActivity().MODE_PRIVATE);

                Gson g = new Gson();
                String j = g.toJson(recipes.getIngredients());

                sharedPreferences.edit().putString("recipeTitle", recipes.getName()).apply();
                sharedPreferences.edit().putString("recipeIngredients", j).apply();

                BakingWidgetProvider.sendRefreshBroadcast(getActivity());

            }
        });

        return view;

    }

    @Override
    public void onStepClick(int position) {
        Intent intent = new Intent(getActivity(), StepsDetails.class);
        intent.putExtra("position", position);
        intent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) steps);
        startActivity(intent);
    }
}
