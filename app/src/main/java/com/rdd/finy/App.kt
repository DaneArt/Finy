package com.rdd.finy

import android.app.Application
import com.rdd.finy.di.AppComponent
import com.rdd.finy.di.AppModule
import com.rdd.finy.di.DaggerAppComponent


class App : Application() {

    companion object{
        private var app: App? = null
        fun app(): App? {
            return app
        }
    }

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        app = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
                .build()
    }



    fun appComponent(): AppComponent? {
        return appComponent
    }

}