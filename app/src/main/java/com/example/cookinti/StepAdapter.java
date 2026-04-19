package com.example.cookinti;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private List<String> steps;

    public StepAdapter(List<String> steps) {
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This links the Adapter to your "Page" XML
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step_page, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        // This links the Data to the specific TextViews in the "Page" XML
        String currentStep = steps.get(position);
        holder.tvStepNumber.setText("Step " + (position + 1));
        holder.tvStepDescription.setText(currentStep);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public static class StepViewHolder extends RecyclerView.ViewHolder {
        TextView tvStepDescription, tvStepNumber;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStepDescription = itemView.findViewById(R.id.tvStepDescription);
            tvStepNumber = itemView.findViewById(R.id.tvStepNumber);
        }
    }
}
