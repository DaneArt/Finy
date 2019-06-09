package com.rdd.finy.helpers;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.rdd.finy.data.WalletDao;
import com.rdd.finy.repository.FinyDatabase;
import com.rdd.finy.repository.WalletDataSource;
import com.rdd.finy.repository.WalletRepository;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class RoomModule {

    private FinyDatabase finyDatabase;

    public RoomModule(Application mApplication) {
        finyDatabase = Room.databaseBuilder(mApplication, FinyDatabase.class, "finy-db").build();
    }

    @Singleton
    @Provides
    FinyDatabase providesRoomDatabase() {
        return finyDatabase;
    }

    @Singleton
    @Provides
    WalletDao providesWalletDao(FinyDatabase finyDatabase) {
        return finyDatabase.getWalletDao();
    }

    @Singleton
    @Provides
    WalletRepository walletRepository(WalletDao walletDao) {
        return new WalletDataSource(walletDao);
    }

}
