package com.rdd.finy.views


import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface InfoView : MvpView {

    fun showControlMoneyDialog(isAddingMoney : Boolean)

    fun updateTotalBalanceState(totalBalance : Double)

    fun expandFAB()

    fun hideFAB()

}