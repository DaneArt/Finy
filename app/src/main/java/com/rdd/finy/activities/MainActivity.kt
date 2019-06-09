package com.rdd.finy.activities

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.rdd.finy.R
import com.rdd.finy.repository.WalletRepository
import javax.inject.Inject
import android.widget.Toast
import com.rdd.finy.data.Wallet
import com.rdd.finy.helpers.RoomModule
import com.rdd.finy.helpers.AppModule
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : AppCompatActivity() {

    @Inject
    private lateinit var productRepository: WalletRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        DaggerAppComponent.builder()
//            .appModule(AppModule(application))
//            .roomModule(RoomModule(application))
//            .build()
//            .inject(this)


        productRepository.findAll().observe(this, Observer<List<Wallet>> { wallets ->
                Toast.makeText(this@MainActivity, String.format("Wallet size: %s", wallets?.size), Toast.LENGTH_SHORT)
            .show()
        })
    }
}