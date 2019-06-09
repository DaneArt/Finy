package com.rdd.finy.helpers;

import android.app.Application;
import com.rdd.finy.activities.MainActivity;
import com.rdd.finy.data.WalletDao;
import com.rdd.finy.repository.FinyDatabase;
import com.rdd.finy.repository.WalletRepository;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class, RoomModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    WalletDao walletDao();

    FinyDatabase finyDatabase();

    WalletRepository walletRepository();

    Application application();

}
