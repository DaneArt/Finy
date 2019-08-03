package com.rdd.finy

import com.rdd.finy.app.models.Wallet
import com.rdd.finy.helpers.AddCalculator
import com.rdd.finy.helpers.CalculatorBeverage
import com.rdd.finy.helpers.RemoveCalculator
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CalculatorTest {

    private lateinit var removeCalc: CalculatorBeverage
    private lateinit var addCalc: CalculatorBeverage

    private fun `prepare addCalculator`(
        userInput: Int,
        userConfigWallet: HashMap<Wallet, Int>,
        otherWallets: List<Wallet>
    ) {
        addCalc = AddCalculator(
            userBalance = userInput, userConfigWallets = userConfigWallet,
            otherWallets = otherWallets
        )
    }

    fun `prepare removeCalculator`(userInput: Int, userConfigWallet: HashMap<Wallet, Int>, otherWallets: List<Wallet>) {
        removeCalc = RemoveCalculator(
            userBalance = userInput, userConfigWallets = userConfigWallet,
            otherWallets = otherWallets
        )
    }

    private fun `prepare addCalculator only with userConfig wallets`() {
        val usersWallets = hashMapOf<Wallet, Int>()
        usersWallets.put(Wallet(id = 1), 200)
        usersWallets.put(Wallet(id = 2), 300)
        `prepare addCalculator`(userInput = 500, userConfigWallet = usersWallets, otherWallets = emptyList())
    }

    private fun `prepare addCalculator only with other wallets`() {
        val others = arrayListOf<Wallet>()
        others.add(Wallet(id = 1))
        others.add(Wallet(id = 2))
        `prepare addCalculator`(userInput = 500, userConfigWallet = hashMapOf(), otherWallets = others)
    }

    @Test
    fun `check that result list is not empty with ideal userConfig wallets`() {
        `prepare addCalculator only with userConfig wallets`()

        val resultList = addCalc.getCalculatedResult()

        assertEquals(true, resultList.isNotEmpty())
    }

    @Test
    fun `check that user input is fully used with ideal userConfig wallets`() {
        `prepare addCalculator only with userConfig wallets`()

        val resultList = addCalc.getCalculatedResult()
        val resultUserInput = addCalc.userBalance

        assertEquals(0, resultUserInput)
    }

    @Test
    fun `check that result list is not empty with ideal other wallets`() {
        `prepare addCalculator only with other wallets`()

        val resultList = addCalc.getCalculatedResult()

        assertEquals(true, resultList.isNotEmpty())
    }

    @Test
    fun `check that user input is fully used with ideal other wallets`() {
        `prepare addCalculator only with other wallets`()

        val resultList = addCalc.getCalculatedResult()
        val resultUserInput = addCalc.userBalance

        assertEquals(0, resultUserInput)
    }

}