package com.rdd.finy.helpers

import android.app.Application
import com.rdd.finy.di.*

class App : Application() {

    companion object
    {
        lateinit var appComponent: AppComponent

    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
    }

}