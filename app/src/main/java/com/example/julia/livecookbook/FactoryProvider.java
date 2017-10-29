package com.example.julia.livecookbook;

import android.content.Context;

import com.example.julia.livecookbook.data.RepositoryFactory;
import com.example.julia.livecookbook.domain.InteractorFactory;

/**
 * Created by julia on 28.10.17.
 */

public class FactoryProvider {
    private RepositoryFactory repositoryFactory;
    private InteractorFactory interactorFactory;

    private Context context;

    public FactoryProvider(Context context) {
        this.context = context;
    }

    public synchronized RepositoryFactory getRepositoryFactory() {
        if (repositoryFactory == null) {
            repositoryFactory = RepositoryFactory.getInstance(context);
        }
        return  repositoryFactory;
    }

    public synchronized InteractorFactory getInteractorFactory() {
        if (interactorFactory == null) {
            interactorFactory = InteractorFactory.getInstance();
        }
        return interactorFactory;
    }
}
