package android.example.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ItemViewHolder> {

    private List<Steps> stepsList;

    public StepsAdapter(List<Steps> stepsList){
        this.stepsList = stepsList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.steps_items, viewGroup, false);

        return new StepsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Steps steps = stepsList.get(i);

        if(steps.getId().equals("0")){
            itemViewHolder.textView.setText(steps.getShortDescription());
        }else {
            itemViewHolder.textView.setText("STEP #" + steps.getId() + " " + steps.getShortDescription());
        }

        itemViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return  stepsList == null ? 0 : stepsList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.steps_tv);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
