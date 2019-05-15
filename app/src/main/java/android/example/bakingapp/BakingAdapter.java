package android.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.ItemViewHolder> {

    private Context context;
    private List<Recipes> recipe;

    public BakingAdapter(Context context, List<Recipes> recipe){
        this.context = context;
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.baking_items, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {

        itemViewHolder.recipeName.setText(recipe.get(i).getName());

        if(TextUtils.isEmpty(recipe.get(i).getImage())){

            itemViewHolder.imageView.setImageResource(R.drawable.baking_image);
        }else {
            Picasso.get()
                    .load(recipe.get(i).getImage())
                    .into(itemViewHolder.imageView);
        }

        itemViewHolder.imageView.setOnClickListener(view -> {
            Intent intent = new Intent(context, RecipeDetails.class);
            intent.putExtra("recipe", recipe.get(i));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return recipe.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView recipeName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.baking_image);
            recipeName = itemView.findViewById(R.id.baking_name);

        }
    }
}
