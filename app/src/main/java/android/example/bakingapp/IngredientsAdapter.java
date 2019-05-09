package android.example.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ItemViewHolder> {

    private List<Ingredients> ingredientsList;

    public IngredientsAdapter(List<Ingredients> ingredientsList){
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredients_items, viewGroup, false);

        return new IngredientsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Ingredients ingredient = ingredientsList.get(i);

        itemViewHolder.ingredients.setText((i+1) + "- " + ingredient.getIngredient() + ", " + ingredient.getQuantity() + " " + ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return  ingredientsList == null ? 0 : ingredientsList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView ingredients;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredients = itemView.findViewById(R.id.ingredients_list);
        }
    }
}
