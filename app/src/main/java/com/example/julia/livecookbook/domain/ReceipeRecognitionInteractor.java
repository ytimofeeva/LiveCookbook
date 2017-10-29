package com.example.julia.livecookbook.domain;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by julia on 28.10.17.
 */

public interface ReceipeRecognitionInteractor  {

    Completable saveReceipe();

    void newReceipe();
    void nextStep();
    void newRecognitionRequest();
    Observable<List<String>> getCurrentSteps();
    Observable<String> getCurrentRecognition();
}
