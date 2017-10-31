package com.example.julia.livecookbook.domain;

import com.example.julia.livecookbook.data.storage.entities.ReceipeDB;

import io.reactivex.Single;

/**
 * Created by julia on 30.10.17.
 */

public interface ReceipeVocalizationInteractor {

    Single<ReceipeDB> getReceipe(String name);
    void vocalizeNextStep();
    void repeatCurrentStep();
    void vocalizeStep(int position);

}
