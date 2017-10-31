package com.example.julia.livecookbook.domain;

import com.example.julia.livecookbook.data.recognition.RecognitionRepository;
import com.example.julia.livecookbook.data.storage.receipe.ReceipeRepository;

/**
 * Created by julia on 28.10.17.
 */

public class InteractorFactory {

    private ReceipeRecognitionInteractor recognitionInteractor;

    private ReceipeVocalizationInteractor vocalizationInteractor;

    public static InteractorFactory getInstance() {
        return new InteractorFactory();
    }

    public synchronized ReceipeRecognitionInteractor getRecognitionInteractor(RecognitionRepository repository,
                                                                              ReceipeRepository receipeRepository) {
        if (recognitionInteractor == null) {
            recognitionInteractor = new ReceipeRecognitionInteractorImpl(repository, receipeRepository);
        }
        return recognitionInteractor;
    }

    public synchronized ReceipeVocalizationInteractor getVocalizationInteractor(RecognitionRepository recognitionRepository,
                                                                                ReceipeRepository receipeRepository) {
        if (vocalizationInteractor == null) {
            vocalizationInteractor = new ReceipeVocalizationInteractorImpl(recognitionRepository, receipeRepository);
        }
        return vocalizationInteractor;
    }



}
