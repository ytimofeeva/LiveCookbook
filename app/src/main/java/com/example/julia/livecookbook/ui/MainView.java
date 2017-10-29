package com.example.julia.livecookbook.ui;

import java.util.List;

/**
 * Created by julia on 27.10.17.
 */

public interface MainView {

    void onStartRecordAudio();
    void onPartialResult(String message);
    void onStopRecognition();
    void showPreviousSteps(List<String> data);
    void showMessage(String msg);
}
