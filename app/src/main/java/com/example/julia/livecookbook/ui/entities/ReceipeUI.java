package com.example.julia.livecookbook.ui.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by julia on 29.10.17.
 */

public class ReceipeUI {

    private String name;
    private String imageUri;

    private List<StepUI> steps;

    public ReceipeUI(@NonNull String name, @Nullable String imageUri, @NonNull List<StepUI> steps) {
        this.name = name;
        this.imageUri = imageUri;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public List<StepUI> getSteps() {
        return steps;
    }
}
