package com.example.julia.livecookbook.ui.receipe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julia.livecookbook.R;
import com.example.julia.livecookbook.ui.entities.ReceipeUI;

/**
 * Created by julia on 30.10.17.
 */

public class ReceipeActivity extends AppCompatActivity implements ReceipeView {

    private TextView name;
    private RecyclerView stepsRecyclerView;
    private ReceipePresenter receipePresenter;

    public static String CURRENT_RECEIPE_TAG = "current_receipe";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe_show);
        name = (TextView) findViewById(R.id.tv_name);
        stepsRecyclerView = (RecyclerView) findViewById(R.id.rv_steps);
        String receipeName = getIntent().getStringExtra(CURRENT_RECEIPE_TAG);
        receipePresenter = new ReceipePresenter(receipeName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        receipePresenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        receipePresenter.detachView();
    }

    @Override
    public void showReceipe(ReceipeUI receipeUI) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(),
                LinearLayoutManager.VERTICAL);
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.addItemDecoration(itemDecoration);
        ReceipeAdapter adapter = new ReceipeAdapter(receipeUI.getSteps(), receipePresenter);
        name.setText(receipeUI.getName());
        stepsRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
