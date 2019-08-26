package com.rdd.finy.app.views

import com.rdd.finy.app.models.Wallet
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = SingleStateStrategy::class)
interface ControlMoneyView : MvpView {

    fun setupSourceList(wallets : List<Wallet>)

    fun setupRvVisibility()

    fun openWalletsList()

    fun closeWalletsList()

    fun loadAnimations()
}