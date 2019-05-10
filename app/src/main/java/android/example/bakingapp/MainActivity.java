package android.example.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private API api;
    private final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private List<Recipes> recipes;
    private BakingAdapter bakingAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_baking_recipes);

        if(MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        }

        fetchData();
    }

    private void fetchData() {

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        api = retrofit.create(API.class);

        Call<List<Recipes>> recipe = api.getAllRecipes();

        recipe.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {

                recipes = response.body();

                bakingAdapter = new BakingAdapter(MainActivity.this, recipes);
                recyclerView.setAdapter(bakingAdapter);
                bakingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
