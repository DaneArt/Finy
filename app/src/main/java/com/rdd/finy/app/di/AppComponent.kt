package com.rdd.finy.app.di

import com.rdd.finy.app.activities.InfoActivity
import com.rdd.finy.app.fragments.SetupWalletDialog
import com.rdd.finy.app.fragments.WalletsContainerFragment
import com.rdd.finy.app.presenters.ControlMoneyPresenter
import com.rdd.finy.helpers.CalculatorBehaviour
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    //Activities
    fun inject(infoActivity: InfoActivity)

    //Fragments
    fun inject(walletsContainerFragment: WalletsContainerFragment)

    //Dialogs
    fun inject(setupWalletDialog: SetupWalletDialog)

    //Presenters
    fun inject(controlMoneyPresenter: ControlMoneyPresenter)

    //Helpers
    fun inject(calculatorBehaviour: CalculatorBehaviour)
}