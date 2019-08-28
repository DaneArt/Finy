package com.rdd.finy.app.views


import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface InfoView : MvpView {

    fun showControlMoneyDialog(isAddingMoney : Boolean)

    fun updateTotalBalanceState(totalBalance: Long)

}