package com.example.julia.livecookbook.ui.receipe;

import android.support.annotation.NonNull;

import com.example.julia.livecookbook.CookbookApplication;
import com.example.julia.livecookbook.data.recognition.RecognitionRepository;
import com.example.julia.livecookbook.data.storage.entities.ReceipeDB;
import com.example.julia.livecookbook.data.storage.receipe.ReceipeRepository;
import com.example.julia.livecookbook.domain.ReceipeVocalizationInteractor;
import com.example.julia.livecookbook.ui.entities.ReceipeUI;
import com.example.julia.livecookbook.ui.entities.StepUI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by julia on 30.10.17.
 */

public class ReceipePresenter {
    private RecognitionRepository repository;
    private ReceipeVocalizationInteractor receipeVocalizationInteractor;
    private ReceipeView view;
    private String receipeName;
    private CompositeDisposable observableContainer;


    public ReceipePresenter(String receipeName) {
        this.repository = CookbookApplication.getInstance().getFactoryProvider().
                getRepositoryFactory()
                .getRecognitionRepository();
        ReceipeRepository receipeRepository = CookbookApplication.getInstance().getFactoryProvider()
                .getRepositoryFactory()
                .getReceipeRepository();
        this.receipeVocalizationInteractor = CookbookApplication.getInstance().getFactoryProvider()
                .getInteractorFactory().getVocalizationInteractor(this.repository, receipeRepository);
        observableContainer = new CompositeDisposable();
        this.receipeName = receipeName;
    }

    public void attachView(@NonNull ReceipeView view) {
        this.view = view;
        observableContainer.add(receipeVocalizationInteractor.getReceipe(receipeName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(receipeDB -> {
                    if (view != null) {
                        view.showReceipe(convertReceipeToUI(receipeDB));
                    }
                }, Timber::e));
    }

    public void detachView() {
        this.view = null;
        observableContainer.clear();
    }

    private ReceipeUI convertReceipeToUI(ReceipeDB receipeDB) {
        List<StepUI> steps = new ArrayList<>();
        for(String s: receipeDB.getSteps()) {
            StepUI newStep = new StepUI(s);
            steps.add(newStep);
        }
        return new ReceipeUI(receipeDB.getName(), receipeDB.getPhotoUri(), steps);
    }

    public void vocalizeStep(int position) {
        receipeVocalizationInteractor.vocalizeStep(position);
    }
}
