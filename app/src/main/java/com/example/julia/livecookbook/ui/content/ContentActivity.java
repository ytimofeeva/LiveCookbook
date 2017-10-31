package com.example.julia.livecookbook.ui.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julia.livecookbook.R;
import com.example.julia.livecookbook.ui.creator.CreateReceipeActivity;
import com.example.julia.livecookbook.ui.entities.ReceipeUI;
import com.example.julia.livecookbook.ui.receipe.ReceipeActivity;

import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by julia on 31.10.17.
 */

public class ContentActivity extends AppCompatActivity implements ContentView, View.OnClickListener {

    private RecyclerView recyclerView;
    private ContentPresenter contentPresenter;
    private TextView textViewEmpty;
    private FloatingActionButton fabNewReceipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        recyclerView = (RecyclerView) findViewById(R.id.rv_content);
        textViewEmpty = (TextView) findViewById(R.id.tv_empty_db);
        fabNewReceipe = (FloatingActionButton) findViewById(R.id.fab_new_receipe);
        fabNewReceipe.setOnClickListener(this);
        contentPresenter = new ContentPresenter();
    }

    @Override
    public void showReceipes(List<ReceipeUI> data) {
        textViewEmpty.setVisibility(INVISIBLE);
        recyclerView.setVisibility(VISIBLE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        ContentAdapter adapter = new ContentAdapter(data, contentPresenter);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showReceipe(String name) {
        Intent intent = new Intent(this, ReceipeActivity.class);
        intent.putExtra(ReceipeActivity.CURRENT_RECEIPE_TAG, name);
        startActivity(intent);
    }

    @Override
    public void showEmptyView() {
        recyclerView.setVisibility(INVISIBLE);
        textViewEmpty.setVisibility(VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        contentPresenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        contentPresenter.detachView();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id) {
            case R.id.fab_new_receipe:
                contentPresenter.moveToReceipeCreateScreen();
        }
    }

    @Override
    public void showCreateReceipeScreen() {
        Intent intent = new Intent(this, CreateReceipeActivity.class);
        startActivity(intent);
    }
}
