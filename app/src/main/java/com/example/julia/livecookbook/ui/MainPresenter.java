package com.example.julia.livecookbook.ui;

import android.support.annotation.NonNull;

import com.example.julia.livecookbook.CookbookApplication;
import com.example.julia.livecookbook.data.recognition.RecognitionRepository;
import com.example.julia.livecookbook.data.storage.receipe.ReceipeRepository;
import com.example.julia.livecookbook.domain.ReceipeRecognitionInteractor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by julia on 27.10.17.
 */

public class MainPresenter {

    private RecognitionRepository repository;
    private ReceipeRecognitionInteractor receipeRecognitionInteractor;
    private MainView view;
    private CompositeDisposable observableContainer;

    public MainPresenter() {
        this.repository = CookbookApplication.getInstance().getFactoryProvider().
                getRepositoryFactory()
                .getRecognitionRepository();
        ReceipeRepository receipeRepository = CookbookApplication.getInstance().getFactoryProvider()
                .getRepositoryFactory()
                .getReceipeRepository();
        this.receipeRecognitionInteractor = CookbookApplication.getInstance().getFactoryProvider()
                .getInteractorFactory().getRecognitionInteractor(this.repository, receipeRepository);
        observableContainer = new CompositeDisposable();
    }

    public void attachView(@NonNull MainView view) {
        this.view = view;
        observableContainer.add(receipeRecognitionInteractor.getCurrentSteps()
                .subscribe(data -> {
                    view.showPreviousSteps(data);
                }, Timber::e));
    }

    public void detachView() {
        this.view = null;
        observableContainer.clear();
    }

    public void startRecognition() {
        receipeRecognitionInteractor.newRecognitionRequest();
        receipeRecognitionInteractor.getCurrentRecognition()
                .subscribe(message -> {
                    if (view != null) {
                        view.onPartialResult(message);
                    }
                }, Timber::e);
    }

    public void startVocalization() {
        repository.startVocalizationRequest("Это я с кем разговариваю?");
    }

    public void nextStep() {
        receipeRecognitionInteractor.nextStep();
    }

    public void saveReceipe() {
        receipeRecognitionInteractor.saveReceipe()
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

}
