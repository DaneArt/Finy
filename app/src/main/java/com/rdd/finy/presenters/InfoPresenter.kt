package com.rdd.finy.presenters

import com.rdd.finy.views.InfoView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class InfoPresenter : MvpPresenter<InfoView>() {

    fun removeMoney() {
        viewState.showControlMoneyDialog(isAddingMoney = false)
    }

    fun addMoney() {
        viewState.showControlMoneyDialog(isAddingMoney = true)
    }

}