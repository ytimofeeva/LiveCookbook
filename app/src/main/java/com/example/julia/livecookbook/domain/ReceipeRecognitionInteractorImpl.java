package com.example.julia.livecookbook.domain;

import android.text.TextUtils;

import com.example.julia.livecookbook.data.recognition.RecognitionRepository;
import com.example.julia.livecookbook.data.storage.entities.ReceipeDB;
import com.example.julia.livecookbook.data.storage.receipe.ReceipeRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by julia on 28.10.17.
 */

public class ReceipeRecognitionInteractorImpl implements ReceipeRecognitionInteractor {

    private RecognitionRepository recognitionRepository;
    private ReceipeRepository receipeRepository;

    private List<String> steps;
    private String currentRecognitionAnswer;
    private PublishSubject currentRecognition;
    private BehaviorSubject currentSteps;
    private String recognizedTextBuffer;
    private static final String EMPTY_NAME = "The name is empty";

    public ReceipeRecognitionInteractorImpl(RecognitionRepository repository, ReceipeRepository receipeRepository) {
        recognitionRepository = repository;
        this.receipeRepository = receipeRepository;
        steps = new ArrayList<>();
        steps.add("");
        recognizedTextBuffer = "";

    }


    @Override
    public Completable saveReceipe(String name) {
        if (TextUtils.isEmpty(name)) {
            return Completable.error(new Throwable(EMPTY_NAME));
        }
        currentSteps.onComplete();
        ReceipeDB receip = new ReceipeDB(name, null, steps);
        if (currentRecognitionAnswer != null) {
            steps.add(currentRecognitionAnswer);
        }
        return receipeRepository.saveReceipe(receip);
    }

    @Override
    public void newReceipe() {
        steps.clear();
        steps.add("");
        currentSteps = BehaviorSubject.create();
        currentSteps.onNext(steps);
        currentRecognitionAnswer = null;
    }

    @Override
    public void nextStep() {
      //  steps.remove(steps.size() - 1);
     //   steps.add(recognizedTextBuffer);
        steps.add("");
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
                            steps.remove(steps.size() - 1);
                            steps.add(recognizedTextBuffer + currentRecognitionAnswer);
                            currentSteps.onNext(steps);
                        },
                        error -> currentRecognition.onError(error),
                        () -> {
                            currentRecognition.onComplete();
                            recognizedTextBuffer += currentRecognitionAnswer;
                        });
    }

    @Override
    public Observable<List<String>> getCurrentSteps() {
        return currentSteps.doOnSubscribe(subscriber -> {
            Timber.d("current steps onSubscribe");
        });
    }

    @Override
    public Observable<String> getCurrentRecognition() {
        return currentRecognition;
    }

}
