package com.example.julia.livecookbook.ui.content;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.julia.livecookbook.R;
import com.example.julia.livecookbook.ui.entities.ReceipeUI;

import java.util.List;

/**
 * Created by julia on 31.10.17.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ReceipeViewHolder> {

    private List<ReceipeUI> receipeUIList;
    private ContentPresenter presenter;

    public ContentAdapter(List<ReceipeUI> data, ContentPresenter presenter) {
        this.receipeUIList = data;
        this.presenter = presenter;
    }

    @Override
    public ReceipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO: custom layuout
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_receipe_step_item, parent, false);
        ReceipeViewHolder holder = new ReceipeViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReceipeViewHolder holder, int position) {
        holder.bindTo(receipeUIList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return receipeUIList.size();
    }

    public class ReceipeViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private LinearLayout itemLinearLayout;

        public ReceipeViewHolder(View v) {
            super(v);
            nameTextView = (TextView) v.findViewById(R.id.tv_step_text);
            itemLinearLayout = (LinearLayout) v.findViewById(R.id.ll_step);
        }

        public void bindTo(ReceipeUI data, int position) {
            nameTextView.setText(data.getName());
            itemLinearLayout.setOnClickListener(view -> {
                presenter.moveToDetailScreen(data.getName());
            });
        }
    }
}
