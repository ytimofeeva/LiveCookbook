package com.example.julia.livecookbook.data.storage.receipe;

import com.example.julia.livecookbook.data.storage.entities.ReceipeDB;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by julia on 29.10.17.
 */

public interface ReceipeRepository {

    Completable saveReceipe(ReceipeDB receipe);
    Single<ReceipeDB> getReceipe(String name);
    Single<List<ReceipeDB>> getAllReceipes();

}
