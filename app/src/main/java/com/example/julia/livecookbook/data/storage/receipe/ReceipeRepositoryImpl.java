package com.example.julia.livecookbook.data.storage.receipe;

import android.content.Context;

import com.example.julia.livecookbook.data.storage.database.AppDatabase;
import com.example.julia.livecookbook.data.storage.entities.ReceipeDB;
import com.example.julia.livecookbook.data.storage.entities.ReceipeHeaderDB;
import com.example.julia.livecookbook.data.storage.entities.ReceipeStepDB;

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
        return null;
    }

}
