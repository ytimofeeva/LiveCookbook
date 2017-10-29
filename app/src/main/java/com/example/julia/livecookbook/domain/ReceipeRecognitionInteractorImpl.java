package com.example.julia.livecookbook.domain;

import com.example.julia.livecookbook.data.recognition.RecognitionRepository;
import com.example.julia.livecookbook.data.storage.entities.ReceipeDB;
import com.example.julia.livecookbook.data.storage.receipe.ReceipeRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by julia on 28.10.17.
 */

public class ReceipeRecognitionInteractorImpl implements ReceipeRecognitionInteractor {

    private RecognitionRepository recognitionRepository;
    private ReceipeRepository receipeRepository;

    private List<String> steps;
    private String currentRecognitionAnswer;
    private PublishSubject currentRecognition;
    private PublishSubject currentSteps;
    private String recognizedTextBuffer;

    public ReceipeRecognitionInteractorImpl(RecognitionRepository repository, ReceipeRepository receipeRepository) {
        recognitionRepository = repository;
        this.receipeRepository = receipeRepository;
        steps = new ArrayList<>();
        recognizedTextBuffer = "";
        currentSteps = PublishSubject.create();
    }


    @Override
    public Completable saveReceipe() {
        currentSteps.onComplete();
        ReceipeDB receip = new ReceipeDB("second", null, steps);
        if (currentRecognitionAnswer != null) {
            steps.add(currentRecognitionAnswer);
        }
        return receipeRepository.saveReceipe(receip);
    }

    @Override
    public void newReceipe() {
        steps.clear();
        currentRecognitionAnswer = null;
    }

    @Override
    public void nextStep() {
        steps.add(recognizedTextBuffer);
        currentRecognitionAnswer = null;
        recognizedTextBuffer = "";
        currentSteps.onNext(steps);
    }

    @Override
    public void newRecognitionRequest() {
        recognitionRepository.startRecognitionRequest();
        currentRecognition = PublishSubject.create();
        recognitionRepository.observePartialRecognition()
                .subscribe(message -> {
                            currentRecognitionAnswer = message;
                            currentRecognition.onNext(recognizedTextBuffer + currentRecognitionAnswer);
                        },
                error -> currentRecognition.onError(error),
                        () -> {
                            currentRecognition.onComplete();
                            recognizedTextBuffer += currentRecognitionAnswer;
                        });
    }

    @Override
    public Observable<List<String>> getCurrentSteps() {
        return currentSteps;
    }

    @Override
    public Observable<String> getCurrentRecognition() {
        return currentRecognition;
    }

}
