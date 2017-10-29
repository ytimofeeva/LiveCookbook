package com.example.julia.livecookbook.data.storage.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by julia on 29.10.17.
 */

public class ReceipeDB {

    private String name;

    private String photoUri;

    private List<String> steps;

    public ReceipeDB(@NonNull String name, @Nullable String photoUri, @NonNull List<String> steps) {
        this.name = name;
        this.photoUri = photoUri;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public List<String> getSteps() {
        return steps;
    }
}
