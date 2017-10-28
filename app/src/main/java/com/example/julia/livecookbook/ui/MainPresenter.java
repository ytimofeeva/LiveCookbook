package com.example.julia.livecookbook.ui;

import com.example.julia.livecookbook.data.recognition.RecognitionRepository;
import com.example.julia.livecookbook.data.recognition.RecognitionRepositoryImpl;

import timber.log.Timber;

/**
 * Created by julia on 27.10.17.
 */

public class MainPresenter implements RecognitionRepositoryImpl.RecognitionRepoListener {

    private RecognitionRepository repository;
    private MainView view;

    public MainPresenter() {
        this.repository = new RecognitionRepositoryImpl(this);
    }

    public void attachView(MainView view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }

    public void startRecognition() {
        repository.startRecognitionRequest();
    }

    public void startVocalization() {
        repository.startVocalizationRequest("Это я с кем разговариваю?");
    }

    @Override
    public void onStartRecord() {
        view.onStartRecordAudio();
    }

    @Override
    public void onPartialResult(String text) {
        view.onPartialResult(text);
    }

    @Override
    public void onStopRecognition() {
        view.onStopRecognition();
    }

    @Override
    public void onError(String error) {
        Timber.e(error);
    }
}
