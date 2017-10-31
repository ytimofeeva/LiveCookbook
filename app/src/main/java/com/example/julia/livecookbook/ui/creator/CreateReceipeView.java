package com.example.julia.livecookbook.ui.creator;

import com.example.julia.livecookbook.ui.entities.StepUI;

import java.util.List;

/**
 * Created by julia on 27.10.17.
 */

public interface CreateReceipeView {

    void onStartRecordAudio();
    void onPartialResult(String message, int position);
    void onStopRecognition();
    void showPreviousSteps(List<StepUI> data);
    void showMessage(String msg);
    void showNewStep(StepUI stepUI);
    void updateStep(int position, StepUI stepUI);
}
