package com.example.julia.livecookbook.data.storage.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by julia on 29.10.17.
 */

@Entity
public class ReceipeHeaderDB {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String photoUri;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
