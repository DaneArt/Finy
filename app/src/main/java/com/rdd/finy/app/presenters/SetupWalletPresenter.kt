/*
Copyright 2019 Daniil Artamonov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.rdd.finy.app.presenters

import com.rdd.finy.app.models.Wallet
import com.rdd.finy.app.views.SetupWalletDialogView
import com.rdd.finy.data.repositories.WalletRepositoryImpl
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class SetupWalletPresenter(private val walletRepositoryImpl: WalletRepositoryImpl) :
    MvpPresenter<SetupWalletDialogView>() {

    fun getSaveableMoney(userInput: String): Long {
        val balance = userInput.split(".")
        var resultBalance = (balance[0].toLong() * 100)
        if (balance.size > 1 && balance[1].isNotBlank()) {
            val kop = balance[1].toLong()
            resultBalance += if (kop < 100)
                kop
            else
                kop.toString().subSequence(0, 2).toString().toLong()
        }
        return resultBalance
    }

    fun setupEditViewWithWalletFromDB(walletId: Long) {
        walletRepositoryImpl.getById(walletId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Wallet> {
                override fun onSuccess(wallet: Wallet) {
                    viewState.setupEditView(wallet)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(error: Throwable) {
                }
            })
    }

    fun buildBalanceString(value: Long): String =
        if (value < 0) {
            "${(value / 100)}.${(value % 100 * (-1))}"
        } else {
            "${(value / 100)}.${(value % 100)}"
        }


}