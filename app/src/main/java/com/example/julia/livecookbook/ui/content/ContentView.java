package com.example.julia.livecookbook.ui.content;

import android.support.annotation.NonNull;

import com.example.julia.livecookbook.ui.entities.ReceipeUI;

import java.util.List;

/**
 * Created by julia on 31.10.17.
 */

public interface ContentView {

    void showReceipes(List<ReceipeUI> data);
    void showMessage(String msg);
    void showReceipe(@NonNull String name);
    void showEmptyView();
    void showCreateReceipeScreen();

}
