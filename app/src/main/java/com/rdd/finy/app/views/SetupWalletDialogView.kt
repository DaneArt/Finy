package com.rdd.finy.app.views

import com.rdd.finy.app.models.Wallet
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = SingleStateStrategy::class)
interface SetupWalletDialogView : MvpView{

    fun setupCreateView()

    fun setupEditView(wallet: Wallet)

    fun setupViewType()


}