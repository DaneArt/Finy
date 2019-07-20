package com.rdd.finy.helpers

import android.util.Log
import com.rdd.finy.app.models.Wallet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class RemoveCalculator(override var activeWallets: HashMap<Wallet, Double>) : CalculatorBeverage() {

    private val TAG = RemoveCalculator::class.java.simpleName

    private lateinit var disposable: Disposable
    override fun calculate() {
        disposable = walletsRepository.getAllWallets()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { wallets ->
                    autoCalcWallets.addAll(
                            wallets.filter { wallet ->
                                !activeWallets.map {
                                    it.key.id
                                }.contains(wallet.id)
                            })
                    calcActive()
                    calcOthers()
                    sendResultToDB()
                    disposable.dispose()
                }
    }

    override fun calcActive() {
        for (wallet in activeWallets) {
            if (userBalance - wallet.value >= 0
                    && wallet.key.couldBeCalculated(-wallet.value)) {
                wallet.key.balance -= wallet.value
                userBalance -= wallet.value
                Log.e(TAG, "Remove money from active. Balance: $userBalance. Value: ${wallet.value}")
            } else {
                autoCalcWallets.add(wallet.key)
                activeWallets.remove(wallet.key)
            }
        }

    }

    override fun calcOthers() {
        var othersCount = autoCalcWallets.size

        autoCalcWallets.sortBy { it.balance - it.bottomDivider }

        for (wallet in autoCalcWallets) {
            val value: Double = userBalance / othersCount
            Log.e(TAG, "Calculatable: ${wallet.couldBeCalculated(-value)}")
            if (wallet.couldBeCalculated(-value)) {
                wallet.balance -= value
            } else {
                userBalance -= wallet.balance - wallet.bottomDivider
                wallet.balance = wallet.bottomDivider
                othersCount--
            }
            Log.e(TAG, "Remove money from with div. Balance: $userBalance. Value: $value")
        }

        if(userBalance>0){
            val extraWallet = Wallet()
            extraWallet.title = "Extra"
            extraWallet.balance = -userBalance
            walletsRepository.insertWallet(extraWallet)
        }

    }

}