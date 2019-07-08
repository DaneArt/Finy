package com.rdd.finy.views

import com.rdd.finy.data.Wallet
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = SingleStateStrategy::class)
interface SetupWalletDialogView : MvpView{

    fun setupCreateView()

    fun setupEditView(wallet:Wallet)

    fun setupViewType()


}