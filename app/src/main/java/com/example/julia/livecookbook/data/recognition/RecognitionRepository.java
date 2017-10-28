package com.example.julia.livecookbook.data.recognition;

/**
 * Created by julia on 27.10.17.
 */

public interface RecognitionRepository {

    void startRecognitionRequest();
    void startVocalizationRequest(String text);
}
