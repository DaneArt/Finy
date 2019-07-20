package com.rdd.finy.helpers

import android.util.Log
import com.rdd.finy.App
import com.rdd.finy.app.models.Wallet
import com.rdd.finy.data.repositories.WalletRepository
import java.util.*
import javax.inject.Inject

abstract class CalculatorBeverage(
        var userBalance: Double = .0,
        var summaryBalance: Double = .0
) {

    private val TAG = CalculatorBeverage::class.java.simpleName

    init {
        App.app()?.appComponent()?.inject(this@CalculatorBeverage)
    }

    @Inject
    lateinit var walletsRepository: WalletRepository

    var autoCalcWallets: ArrayList<Wallet> = arrayListOf()

    abstract var activeWallets: HashMap<Wallet, Double>

    abstract fun calculate()

    abstract fun calcActive()

    abstract fun calcOthers()

    fun sendResultToDB(){
        val resultList = arrayListOf<Wallet>()
        resultList.addAll(activeWallets.map { it.key })
        resultList.addAll(autoCalcWallets)

        Log.e(TAG, Arrays.toString(resultList.map { it.id }.toLongArray()))
        walletsRepository.updateWallets(resultList)
    }
}