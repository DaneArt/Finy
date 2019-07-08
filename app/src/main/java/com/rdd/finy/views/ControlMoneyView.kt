package com.rdd.finy.views

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = SingleStateStrategy::class)
interface ControlMoneyView : MvpView {

    fun setupInsertView()

    fun setupRemoveView()
}