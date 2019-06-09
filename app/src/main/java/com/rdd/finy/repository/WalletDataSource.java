package com.rdd.finy.repository;

import android.arch.lifecycle.LiveData;
import com.rdd.finy.data.Wallet;
import com.rdd.finy.data.WalletDao;

import javax.inject.Inject;
import java.util.List;

public class WalletDataSource implements WalletRepository {

    private WalletDao walletDao;

    @Inject
    public WalletDataSource(WalletDao walletDao) {
        this.walletDao = walletDao;
    }

    @Override
    public LiveData<Wallet> findById(int id) {
        return walletDao.findById(id);
    }

    @Override
    public LiveData<List<Wallet>> findAll() {
        return walletDao.findAll();
    }

    @Override
    public long insert(Wallet wallet) {
        return walletDao.insert(wallet);
    }

    @Override
    public int delete(Wallet wallet) {
        return walletDao.delete(wallet);
    }
}
