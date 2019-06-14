package com.rdd.finy.helpers

import android.app.Application
import com.rdd.finy.di.AppComponent
import com.rdd.finy.di.AppModule
import com.rdd.finy.di.DaggerAppComponent


class FinyApp : Application() {

    companion object{
        private var app: FinyApp? = null
        fun app(): FinyApp? {
            return app
        }
    }

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        app = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext)).build()
    }



    fun appComponent(): AppComponent? {
        return appComponent
    }

}