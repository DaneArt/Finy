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

import android.content.Context
import android.graphics.Color
import com.rdd.finy.App
import com.rdd.finy.app.models.Wallet
import com.rdd.finy.app.views.BalanceChartView
import com.rdd.finy.data.repositories.WalletRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class BalanceChartPresenter(private val context: Context) : MvpPresenter<BalanceChartView>() {

    private val TAG = BalanceChartPresenter::class.java.simpleName

    init {
        App.app()?.appComponent()?.inject(this)
    }

    @Inject
    lateinit var walletsRepositoryImpl: WalletRepositoryImpl

    private lateinit var disposable: Disposable

    fun loadChartData() {
        disposable = walletsRepositoryImpl.getActiveWallets()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ wallets ->
                updateStatsDiagramState(wallets = wallets)
            }, {
                viewState.showError(it.localizedMessage)
            })
    }

    private fun updateStatsDiagramState(wallets: List<Wallet>) {

        val statsChartData: PieChartData

        val values = ArrayList<SliceValue>()

        for (wallet in wallets.filter { it.balance > 0 }) {

            val sliceValue = SliceValue(
                (wallet.balance / 100) + (wallet.balance % 100 / 100f),
                Color.parseColor(wallet.backColor.split("|")[1])
            )
            values.add(sliceValue)
        }

        statsChartData = PieChartData(values)
        statsChartData.setHasLabels(true)
        statsChartData.setHasLabelsOnlyForSelected(false)
        statsChartData.setHasLabelsOutside(false)
        statsChartData.setHasCenterCircle(true)

        if (values.isEmpty()) {
            viewState.showNoActiveWalletsWithPositiveBalance()
        } else {
            viewState.updatePieData(statsChartData)
        }

    }

    fun dispose() {
        disposable.dispose()
    }

}