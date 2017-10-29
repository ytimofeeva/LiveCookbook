package com.example.julia.livecookbook.data.storage.entities;

import android.arch.persistence.room.Entity;

/**
 * Created by julia on 29.10.17.
 */
@Entity(primaryKeys = {"receipId", "stepNumber"})
public class ReceipeStepDB {

    private int receipId;

    private int stepNumber;

    private String text;

    public int getReceipId() {
        return receipId;
    }

    public void setReceipId(int receipId) {
        this.receipId = receipId;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
