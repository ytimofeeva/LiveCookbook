package com.example.julia.livecookbook.ui.receipe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.julia.livecookbook.R;
import com.example.julia.livecookbook.ui.entities.StepUI;

import java.util.List;

/**
 * Created by julia on 30.10.17.
 */

public class ReceipeAdapter extends RecyclerView.Adapter<ReceipeAdapter.StepViewHolder> {

    private List<StepUI> steps;
    private ReceipePresenter receipePresenter;

    public ReceipeAdapter(List<StepUI> steps, ReceipePresenter presenter) {
        this.steps = steps;
        this.receipePresenter = presenter;
    }
    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_receipe_step_item, parent, false);
        StepViewHolder vh = new StepViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.bindTo(steps.get(position), position);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder {
        private TextView stepNumber;
        private TextView stepText;
        private LinearLayout item;


        public StepViewHolder(View view) {
            super(view);
            stepNumber = (TextView) view.findViewById(R.id.tv_step_number);
            stepText = (TextView) view.findViewById(R.id.tv_step_text);
            item = (LinearLayout) view.findViewById(R.id.ll_step);
        }

        public void bindTo(StepUI stepUI, int position) {
            stepNumber.setText(String.valueOf(position));
            stepText.setText(stepUI.getText());
            item.setOnClickListener(view -> {
                receipePresenter.vocalizeStep(position);
            });
        }

    }
}
