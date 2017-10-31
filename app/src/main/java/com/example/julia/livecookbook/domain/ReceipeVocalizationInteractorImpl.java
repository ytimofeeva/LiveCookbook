package com.example.julia.livecookbook.domain;

import com.example.julia.livecookbook.data.recognition.RecognitionRepository;
import com.example.julia.livecookbook.data.storage.entities.ReceipeDB;
import com.example.julia.livecookbook.data.storage.receipe.ReceipeRepository;

import io.reactivex.Single;

/**
 * Created by julia on 30.10.17.
 */

public class ReceipeVocalizationInteractorImpl implements ReceipeVocalizationInteractor {

    private ReceipeRepository receipeRepository;
    private RecognitionRepository recognitionRepository;
    private ReceipeDB vocalizationReceipe;
    private int stepToVocalize;

    public ReceipeVocalizationInteractorImpl(RecognitionRepository recognitionRepository,
                                             ReceipeRepository receipeRepository) {
        this.receipeRepository = receipeRepository;
        this.recognitionRepository = recognitionRepository;
    }
    @Override
    public Single<ReceipeDB> getReceipe(String name) {

        return receipeRepository.getReceipe(name)
                .doOnSuccess(receipe -> {
                    vocalizationReceipe = receipe;
                    stepToVocalize = -1;
                });

    }

    @Override
    public void vocalizeNextStep() {
        stepToVocalize++;
        recognitionRepository.startVocalizationRequest(vocalizationReceipe.getSteps().get(stepToVocalize));
    }

    @Override
    public void repeatCurrentStep() {
        recognitionRepository.startVocalizationRequest(vocalizationReceipe.getSteps().get(stepToVocalize));
    }

    @Override
    public void vocalizeStep(int position) {
        recognitionRepository.startVocalizationRequest(vocalizationReceipe.getSteps().get(position));
    }
}
