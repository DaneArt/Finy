package com.rdd.finy.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.rdd.finy.data.Wallet;
import com.rdd.finy.data.WalletDao;


@Database(entities = {Wallet.class}, version = FinyDatabase.VERSION)
public abstract class FinyDatabase extends RoomDatabase {

    static final int VERSION = 1;

    public abstract WalletDao getWalletDao();

}
