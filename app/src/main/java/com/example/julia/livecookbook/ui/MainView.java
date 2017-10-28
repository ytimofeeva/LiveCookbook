package com.example.julia.livecookbook.ui;

/**
 * Created by julia on 27.10.17.
 */

public interface MainView {

    void onStartRecordAudio();
    void onPartialResult(String message);
    void onStopRecognition();
}
