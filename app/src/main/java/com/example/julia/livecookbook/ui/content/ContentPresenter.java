package com.example.julia.livecookbook.ui.content;

import android.support.annotation.NonNull;

import com.example.julia.livecookbook.CookbookApplication;
import com.example.julia.livecookbook.data.storage.entities.ReceipeDB;
import com.example.julia.livecookbook.data.storage.receipe.ReceipeRepository;
import com.example.julia.livecookbook.ui.entities.ReceipeUI;
import com.example.julia.livecookbook.ui.entities.StepUI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by julia on 31.10.17.
 */

public class ContentPresenter {

    private ReceipeRepository receipeRepository;
    private ContentView view;
    private CompositeDisposable observableContainer;

    public ContentPresenter() {
        this.receipeRepository = CookbookApplication.getInstance()
                .getFactoryProvider().getRepositoryFactory().getReceipeRepository();
        observableContainer = new CompositeDisposable();
    }

    public void attachView(@NonNull ContentView view) {
        this.view = view;
        observableContainer.add(receipeRepository.getAllReceipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (data.size() > 0) {
                        view.showReceipes(convertReceipeListToUI(data));
                    } else {
                        view.showEmptyView();
                    }
                }, Timber::e));
    }

    public void detachView() {
        this.view = null;
        observableContainer.clear();
    }

    public void moveToDetailScreen(String receipeName) {
        if (view != null) {
            view.showReceipe(receipeName);
        }
    }

    public void moveToReceipeCreateScreen() {
        if (view != null) {
            view.showCreateReceipeScreen();
        }
    }

    private ReceipeUI convertReceipeToUI(ReceipeDB receipeDB) {
        List<StepUI> steps = new ArrayList<>();
        for(String s: receipeDB.getSteps()) {
            StepUI newStep = new StepUI(s);
            steps.add(newStep);
        }
        return new ReceipeUI(receipeDB.getName(), receipeDB.getPhotoUri(), steps);
    }

    private List<ReceipeUI> convertReceipeListToUI(List<ReceipeDB> data) {
        List<ReceipeUI> result = new ArrayList<>();
        for(ReceipeDB receipeDB : data) {
            ReceipeUI receipeUI = convertReceipeToUI(receipeDB);
            result.add(receipeUI);
        }
        return result;
    }
}
