package com.example.julia.livecookbook.ui.creator;

import android.support.annotation.NonNull;

import com.example.julia.livecookbook.CookbookApplication;
import com.example.julia.livecookbook.data.recognition.RecognitionRepository;
import com.example.julia.livecookbook.data.storage.receipe.ReceipeRepository;
import com.example.julia.livecookbook.domain.ReceipeRecognitionInteractor;
import com.example.julia.livecookbook.ui.entities.StepUI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by julia on 27.10.17.
 */

public class CreateReceipePresenter {

    private RecognitionRepository repository;
    private ReceipeRecognitionInteractor receipeRecognitionInteractor;
    private CreateReceipeView view;
    private CompositeDisposable observableContainer;
    private List<String> currentData;

    public CreateReceipePresenter() {
        this.repository = CookbookApplication.getInstance().getFactoryProvider().
                getRepositoryFactory()
                .getRecognitionRepository();
        ReceipeRepository receipeRepository = CookbookApplication.getInstance().getFactoryProvider()
                .getRepositoryFactory()
                .getReceipeRepository();
        this.receipeRecognitionInteractor = CookbookApplication.getInstance().getFactoryProvider()
                .getInteractorFactory().getRecognitionInteractor(this.repository, receipeRepository);
        receipeRecognitionInteractor.newReceipe();
        observableContainer = new CompositeDisposable();
    }

    public void attachView(@NonNull CreateReceipeView view) {
        this.view = view;
        observableContainer.add(receipeRecognitionInteractor.getCurrentSteps()
                .subscribe(data -> {
                               view.showPreviousSteps(convertStepListToUI(data));

                }, Timber::e));
    }

    public void detachView() {
        this.view = null;
        observableContainer.clear();
    }

    public void startRecognition(int position) {
        receipeRecognitionInteractor.newRecognitionRequest();
       /* receipeRecognitionInteractor.getCurrentRecognition()
                .subscribe(message -> {
                    if (view != null) {
                        view.onPartialResult(message, position);
                    }
                }, Timber::e);*/
    }

    public void nextStep() {
        receipeRecognitionInteractor.nextStep();
    }

    public void saveReceipe(String name) {
        receipeRecognitionInteractor.saveReceipe(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            if (view != null)
                                view.showMessage("Save OK");
                        },
                        error -> {
                            if (view != null)
                                view.showMessage(error.getLocalizedMessage());
                        });
    }

    private List<StepUI> convertStepListToUI(List<String> data) {
        List<StepUI> steps = new ArrayList<>();
        for(String s: data) {
            StepUI newStep = new StepUI(s);
            steps.add(newStep);
        }
        return steps;
    }

}
