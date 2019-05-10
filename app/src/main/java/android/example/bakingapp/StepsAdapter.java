package android.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ItemViewHolder> {

    private Context context;
    private List<Steps> stepsList;
    private OnStepListener onStepListener;

    public StepsAdapter(Context context,List<Steps> stepsList, OnStepListener onStepListener){
        this.context = context;
        this.stepsList = stepsList;
        this.onStepListener = onStepListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.steps_items, viewGroup, false);

        return new StepsAdapter.ItemViewHolder(view, onStepListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {

        Steps steps = stepsList.get(i);
        itemViewHolder.textView.setText(steps.getId() + ". " + steps.getShortDescription());


    }

    @Override
    public int getItemCount() {
        return  stepsList == null ? 0 : stepsList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;
        OnStepListener onStepListener;

        public ItemViewHolder(@NonNull View itemView, OnStepListener onStepListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.steps_tv);

            this.onStepListener = onStepListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onStepListener.onStepClick(getAdapterPosition());
        }
    }
    public interface OnStepListener{
        void onStepClick(int position);
    }

}
