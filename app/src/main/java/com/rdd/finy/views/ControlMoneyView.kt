package com.rdd.finy.views

import com.rdd.finy.data.Wallet
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = SingleStateStrategy::class)
interface ControlMoneyView : MvpView {

    fun setupInsertView()

    fun setupRemoveView()

    fun setupSourceList(wallets : List<Wallet>)

    fun setupRvVisibility()

    fun openWalletsList()

    fun closeWalletsList()
}