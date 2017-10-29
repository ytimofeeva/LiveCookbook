package com.example.julia.livecookbook.data;

import android.content.Context;

import com.example.julia.livecookbook.data.recognition.RecognitionRepository;
import com.example.julia.livecookbook.data.recognition.RecognitionRepositoryImpl;
import com.example.julia.livecookbook.data.storage.receipe.ReceipeRepository;
import com.example.julia.livecookbook.data.storage.receipe.ReceipeRepositoryImpl;

/**
 * Created by julia on 28.10.17.
 */

public class RepositoryFactory {

    private Context context;
    private RecognitionRepository recognitionRepository;
    private ReceipeRepository receipeRepository;

    public RepositoryFactory(Context context) {
        this.context = context;
    }

    public static RepositoryFactory getInstance(Context context)
    {
        return new RepositoryFactory(context);
    }

    public synchronized RecognitionRepository getRecognitionRepository() {
        if (recognitionRepository == null) {
            recognitionRepository = new  RecognitionRepositoryImpl();
        }
        return recognitionRepository;
    }

    public synchronized ReceipeRepository getReceipeRepository() {
        if (receipeRepository == null) {
            receipeRepository = ReceipeRepositoryImpl.getIntsance(context);
        }
        return receipeRepository;
    }

}
