package com.example.julia.livecookbook.data.storage.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.julia.livecookbook.data.storage.entities.ReceipeHeaderDB;
import com.example.julia.livecookbook.data.storage.entities.ReceipeStepDB;

import java.util.List;

/**
 * Created by julia on 29.10.17.
 */
@Dao
public interface ReceipeDao {

    @Insert
    void saveReceipeHeader(ReceipeHeaderDB receipeHeaderDB);

    @Insert
    void saveReceipeStep(ReceipeStepDB step);

    @Query("SELECT id FROM ReceipeHeaderDB WHERE name LIKE :name LIMIT 1")
    int getIdForReceip(String name);

    @Query("SELECT * FROM ReceipeStepDB WHERE receipId LIKE :id")
    List<ReceipeStepDB> getAllStepsForReceipeId(int id);


}
