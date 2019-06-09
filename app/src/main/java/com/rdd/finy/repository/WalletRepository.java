package com.rdd.finy.repository;

import android.arch.lifecycle.LiveData;
import com.rdd.finy.data.Wallet;

import java.util.List;

public interface WalletRepository {

    LiveData<Wallet> findById(int id);

    LiveData<List<Wallet>> findAll();

    long insert(Wallet wallet);

    int delete(Wallet wallet);

}
