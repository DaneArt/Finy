package com.rdd.finy.di

import com.rdd.finy.activities.InfoActivity
import com.rdd.finy.fragments.SetupWalletDialog
import com.rdd.finy.fragments.WalletsContainerFragment
import com.rdd.finy.helpers.CalculatorBehaviour
import com.rdd.finy.presenters.ControlMoneyPresenter
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