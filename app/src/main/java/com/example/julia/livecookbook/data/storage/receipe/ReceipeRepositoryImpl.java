package com.example.julia.livecookbook.data.storage.receipe;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.julia.livecookbook.data.storage.database.AppDatabase;
import com.example.julia.livecookbook.data.storage.entities.ReceipeDB;
import com.example.julia.livecookbook.data.storage.entities.ReceipeHeaderDB;
import com.example.julia.livecookbook.data.storage.entities.ReceipeStepDB;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by julia on 29.10.17.
 */

public class ReceipeRepositoryImpl implements ReceipeRepository {

    private Context context;

    public static ReceipeRepository getIntsance(Context context) {
        return new ReceipeRepositoryImpl(context);
    }

    protected ReceipeRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public Completable saveReceipe(ReceipeDB receipe) {
        return Completable.fromAction(() -> {
            ReceipeHeaderDB receipeHeaderDB = new ReceipeHeaderDB();
            receipeHeaderDB.setName(receipe.getName());
            receipeHeaderDB.setPhotoUri(receipe.getPhotoUri());
            AppDatabase.getInstance(context).receipeDao().saveReceipeHeader(receipeHeaderDB);
            int receipId = AppDatabase.getInstance(context).receipeDao().getIdForReceip(receipe.getName());
            int stepId = 0;
            for (String s : receipe.getSteps()) {
                ReceipeStepDB receipeStepDB = new ReceipeStepDB();
                receipeStepDB.setReceipId(receipId);
                receipeStepDB.setStepNumber(stepId);
                stepId++;
                receipeStepDB.setText(s);
                //TODO: delete receipeHeader in error case
                AppDatabase.getInstance(context).receipeDao().saveReceipeStep(receipeStepDB);
            }
        });
    }

    @Override
    public Single<ReceipeDB> getReceipe(String name) {

        return Single.fromCallable(() -> {
            ReceipeHeaderDB header = AppDatabase.getInstance(context).receipeDao().getReceipHeader(name);
            List<ReceipeStepDB> steps = AppDatabase.getInstance(context).receipeDao()
                    .getAllStepsForReceipeId(header.getId());
            //TODO: check for steps
            return convertPartsForReceipeDB(header, steps);
        });
    }

    @Override
    public Single<List<ReceipeDB>> getAllReceipes() {
        return Single.fromCallable(() -> {
            List<ReceipeHeaderDB> headers = AppDatabase.getInstance(context).receipeDao().getAllReceipeHeaders();
            List<ReceipeDB> receips = new ArrayList<ReceipeDB>();
            for(ReceipeHeaderDB header : headers) {
                List<ReceipeStepDB> steps = AppDatabase.getInstance(context).receipeDao()
                        .getAllStepsForReceipeId(header.getId());
                ReceipeDB newReceipe = convertPartsForReceipeDB(header, steps);
                receips.add(newReceipe);
            }
            return receips;
        });

    }

    private ReceipeDB convertPartsForReceipeDB(@NonNull ReceipeHeaderDB header, @NonNull List<ReceipeStepDB> steps) {
        List<String> list = new ArrayList<>();
        for (ReceipeStepDB s : steps) {
            list.add(s.getText());
        }
        return new ReceipeDB(header.getName(), header.getPhotoUri(), list);
    }

}
