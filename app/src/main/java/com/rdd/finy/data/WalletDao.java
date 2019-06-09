package com.rdd.finy.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import java.util.List;

@Dao
public interface WalletDao {

    @Query("SELECT * FROM Wallet WHERE id=:id")
    LiveData<Wallet> findById(int id);

    @Query("SELECT * FROM Wallet")
    LiveData<List<Wallet>> findAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Wallet wallet);

    @Delete
    int delete(Wallet wallet);

}
