package com.rdd.finy.activities;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.rdd.finy.R;
import com.rdd.finy.data.Wallet;
import com.rdd.finy.helpers.AppModule;
import com.rdd.finy.helpers.RoomModule;
import com.rdd.finy.repository.WalletRepository;

import javax.inject.Inject;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Inject
    public WalletRepository walletRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerAppComponent.builder()
                .appModule(new AppModule(getApplication()))
                .roomModule(new RoomModule(getApplication()))
                .build()
                .inject(this);


        walletRepository.findAll().observe(this, new Observer<List<Wallet>>() {
            @Override
            public void onChanged(@Nullable List<Wallet> wallets) {
                Toast.makeText(MainActivity.this, String.format("Product size: %s", wallets.size()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
