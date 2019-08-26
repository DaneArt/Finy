package com.rdd.finy.helpers

import com.rdd.finy.app.models.Wallet

class AddCalculator(
    override var userBalance: Int,
    override var userConfigWallets: HashMap<Wallet, Int>,
    override var otherWallets: List<Wallet>
) : CalculatorBeverage() {

    override fun getCalculatedResult(): List<Wallet> {

        if (userConfigWallets.isNotEmpty())
            calculateUserConfigWallets()
        if (otherWallets.isNotEmpty())
            calculateOtherWallets()
        if (userBalance != 0)
            setupExtraWallet()

        return otherWallets + userConfigWallets.keys
    }

    override fun calculateUserConfigWallets() {
        var diff: Int

        for (item in userConfigWallets) {

            if (item.key.upperDivider != null) {
                diff = item.key.upperDivider!! - item.key.balance

                if (diff < item.value) {
                    item.key.balance = item.key.upperDivider!!
                    userBalance -= diff
                }
            } else if (userBalance < item.value) {
                item.key.balance = userBalance
                userBalance = 0
            } else {
                item.key.balance += item.value
                userBalance -= item.value
            }
        }

    }

    override fun calculateOtherWallets() {
        var partsCount = otherWallets.size
        var part: Int
        var diff: Int

        for (wallet in otherWallets) {

            part = userBalance / partsCount + userBalance % partsCount

            if (wallet.upperDivider != null) {
                diff = wallet.upperDivider!! - wallet.balance
                if (diff < part) {
                    wallet.balance = wallet.upperDivider!!
                    userBalance -= diff
                } else {
                    wallet.balance += part
                    userBalance -= part
                }
            } else {
                wallet.balance += part
                userBalance -= part
            }

            partsCount--
        }
    }

    override fun setupExtraWallet() {
        val extraWallet = Wallet(title = "Extra")
        extraWallet.balance = userBalance

        userConfigWallets[extraWallet] = 0
    }

}