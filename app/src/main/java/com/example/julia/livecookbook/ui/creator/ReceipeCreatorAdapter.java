package com.example.julia.livecookbook.ui.creator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.julia.livecookbook.R;
import com.example.julia.livecookbook.ui.entities.StepUI;

import java.util.List;

/**
 * Created by julia on 31.10.17.
 */

public class ReceipeCreatorAdapter extends RecyclerView.Adapter<ReceipeCreatorAdapter.StepCreatorViewHolder> {

    private List<StepUI> steps;
    private CreateReceipePresenter presenter;

    public ReceipeCreatorAdapter(List<StepUI> steps, CreateReceipePresenter presenter) {
        this.steps = steps;
        this.presenter = presenter;
    }

    @Override
    public StepCreatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_create_step_item, parent, false);
        StepCreatorViewHolder holder = new StepCreatorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StepCreatorViewHolder holder, int position) {
        holder.bindTo(steps.get(position), position);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public int addItem(StepUI step) {
        steps.add(step);
        return steps.size() - 1;
    }

    public void updateItem(int position, String message) {
        steps.remove(position);
        steps.add(position, new StepUI(message));
    }

    public class StepCreatorViewHolder extends RecyclerView.ViewHolder {
        private EditText text;
        private ImageButton nextStep;

        public StepCreatorViewHolder(View view) {
            super(view);
            text = (EditText) view.findViewById(R.id.et_step_text);
            nextStep = (ImageButton) view.findViewById(R.id.ib_next_step);
        }

        public void bindTo(StepUI step, int position) {
            text.setText(step.getText());
            text.setOnClickListener(view -> {
                presenter.startRecognition(position);
            });
            nextStep.setOnClickListener(view -> {
                presenter.nextStep();
            });
        }
    }
}
