package com.rdd.finy

import com.rdd.finy.app.models.Wallet
import com.rdd.finy.helpers.CalculatorBeverage
import com.rdd.finy.helpers.RemoveCalculator
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RemoveCalculatorTest {


    private lateinit var removeCalc: CalculatorBeverage

    private fun `prepare Calculator`(
        userInput: Int,
        userConfigWallet: HashMap<Wallet, Int>,
        otherWallets: List<Wallet>
    ) {
        removeCalc = RemoveCalculator(
            userBalance = userInput, userConfigWallets = userConfigWallet,
            otherWallets = otherWallets
        )
    }

    private fun `prepare Calculator only with ideal userConfig wallets`() {
        val usersWallets = hashMapOf<Wallet, Int>()
        val fWal = Wallet()
        fWal.balance = 200
        val sWal = Wallet()
        sWal.balance = 300
        usersWallets[fWal] = 200
        usersWallets[sWal] = 300
        `prepare Calculator`(userInput = 500, userConfigWallet = usersWallets, otherWallets = emptyList())
    }

    private fun `prepare Calculator only with ideal other wallets`() {
        val others = arrayListOf<Wallet>()
        val fWal = Wallet()
        fWal.balance = 250
        val sWal = Wallet()
        sWal.balance = 250
        others.add(fWal)
        others.add(sWal)
        `prepare Calculator`(userInput = 500, userConfigWallet = hashMapOf(), otherWallets = others)
    }

    private fun `prepare Calculator with ideal both walletLists`() {
        val usersWallets = hashMapOf<Wallet, Int>()

        val fWal = Wallet()
        fWal.balance = 200

        val sWal = Wallet()
        sWal.balance = 300

        usersWallets[fWal] = 200
        usersWallets[sWal] = 300

        val others = arrayListOf<Wallet>()

        val tWal = Wallet()
        tWal.balance = 250

        val foWal = Wallet()
        foWal.balance = 250

        others.add(tWal)
        others.add(foWal)

        `prepare Calculator`(userInput = 1000, userConfigWallet = usersWallets, otherWallets = others)
    }

    private fun assertResultListSize(expectedSize: Int, actualSize: Int) {
        assertEquals(expectedSize, actualSize)
    }

    private fun assertResultListBalances(expectedBalanceArray: IntArray, actualBalanceArray: IntArray) {

        assertEquals(true, expectedBalanceArray.contentEquals(actualBalanceArray))
    }

    @Test
    fun `check that user input is fully used with ideal userConfig wallets`() {
        `prepare Calculator only with ideal userConfig wallets`()

        val resultList = removeCalc.getCalculatedResult()
        val resultUserInput = removeCalc.userBalance

        assertEquals(0, resultUserInput)
    }

    @Test
    fun `assert that result list size is expected with ideal userConfig wallets`() {
        `prepare Calculator only with ideal userConfig wallets`()

        val resultList = removeCalc.getCalculatedResult()

        assertResultListSize(2, resultList.size)
    }

    @Test
    fun `assert that result list has correct items with ideal userConfig wallets`() {
        `prepare Calculator only with ideal userConfig wallets`()

        val resultList = removeCalc.getCalculatedResult()

        assertResultListBalances(intArrayOf(0, 0), resultList.map { it.balance }.sorted().toIntArray())
    }

    @Test
    fun `check that user input is fully used with ideal other wallets`() {
        `prepare Calculator only with ideal other wallets`()

        val resultList = removeCalc.getCalculatedResult()
        val resultUserInput = removeCalc.userBalance

        assertEquals(0, resultUserInput)
    }

    @Test
    fun `assert that result list size is expected with ideal other wallets`() {
        `prepare Calculator only with ideal other wallets`()

        val resultList = removeCalc.getCalculatedResult()

        assertResultListSize(2, resultList.size)
    }

    @Test
    fun `assert that result list has correct items with ideal other wallets`() {
        `prepare Calculator only with ideal other wallets`()

        val resultList = removeCalc.getCalculatedResult()

        assertResultListBalances(intArrayOf(0, 0), resultList.map { it.balance }.toIntArray())
    }

    @Test
    fun `check that user input is fully used with ideal both wallets`() {
        `prepare Calculator with ideal both walletLists`()

        removeCalc.getCalculatedResult()
        val resultUserInput = removeCalc.userBalance

        assertEquals(0, resultUserInput)
    }

    @Test
    fun `assert that result list size is expected with ideal both wallets`() {
        `prepare Calculator with ideal both walletLists`()

        val resultList = removeCalc.getCalculatedResult()

        assertResultListSize(4, resultList.size)
    }

    @Test
    fun `assert that result list has correct items with ideal both wallets`() {
        `prepare Calculator with ideal both walletLists`()

        val resultList = removeCalc.getCalculatedResult()

        assertResultListBalances(intArrayOf(0, 0, 0, 0), resultList.map { it.balance }.sorted().toIntArray())
    }

    @Test
    fun `assert that extraWallet was Created with empty walletsList`() {
        `prepare Calculator`(userInput = 500, userConfigWallet = hashMapOf(), otherWallets = emptyList())

        val result = removeCalc.getCalculatedResult()

        assertEquals(1, result.size)
        assertEquals("Extra", result[0].title)
        assertEquals(-500, result[0].balance)
    }

    @Test
    fun `assert that extraWallet was Created with divider on userConfig wallets`() {
        val testWallet = Wallet()
        testWallet.balance = 400
        testWallet.bottomDivider = 200
        `prepare Calculator`(
            userInput = 500,
            userConfigWallet = hashMapOf(Pair(testWallet, 300)),
            otherWallets = emptyList()
        )

        val result = removeCalc.getCalculatedResult()

        assertEquals(2, result.size)
        assertEquals("Extra", result[0].title)
        assertEquals(-300, result[0].balance)
    }

    @Test
    fun `assert that extraWallet was Created with divider on other wallets`() {
        val testWallet = Wallet()
        testWallet.balance = 400
        testWallet.bottomDivider = 200
        `prepare Calculator`(
            userInput = 500,
            userConfigWallet = hashMapOf(),
            otherWallets = listOf(testWallet)
        )

        val result = removeCalc.getCalculatedResult()

        assertEquals(2, result.size)
        assertEquals("Extra", result[1].title)
        assertEquals(-300, result[1].balance)
    }

    @Test
    fun `assert that extraWallet was Created with divider on both wallets lists`() {
        val testUserWallet = Wallet()
        testUserWallet.balance = 400
        testUserWallet.bottomDivider = 200
        val testOtherWallet = Wallet()
        testOtherWallet.balance = 400
        testOtherWallet.bottomDivider = 200
        `prepare Calculator`(
            userInput = 500,
            userConfigWallet = hashMapOf(Pair(testUserWallet, 300)),
            otherWallets = listOf(testOtherWallet)
        )

        val result = removeCalc.getCalculatedResult()

        assertEquals(3, result.size)
        assertEquals("Extra", result[2].title)
        assertEquals(-100, result[2].balance)
    }

    @Test
    fun `assert that money was correctly distributed with divider on userConfig wallets`() {
        val fWal = Wallet()
        fWal.balance = 400
        fWal.bottomDivider = 200

        val sWal = Wallet()
        sWal.balance = 300

        `prepare Calculator`(
            userInput = 500,
            userConfigWallet = hashMapOf(Pair(fWal, 300), Pair(sWal, 300)),
            otherWallets = emptyList()
        )

        val result = removeCalc.getCalculatedResult()

        assertResultListBalances(intArrayOf(0, 200), result.map { it.balance }.sorted().toIntArray())
    }

    @Test
    fun `assert that money was correctly distributed with divider on other wallets`() {
        val fWal = Wallet()
        fWal.balance = 400
        fWal.bottomDivider = 200

        val sWal = Wallet()
        sWal.balance = 300

        `prepare Calculator`(
            userInput = 500,
            userConfigWallet = hashMapOf(),
            otherWallets = listOf(fWal, sWal)
        )

        val result = removeCalc.getCalculatedResult()

        assertResultListBalances(intArrayOf(0, 200), result.map { it.balance }.sorted().toIntArray())
    }

    @Test
    fun `assert that money was correctly distributed with divider on both wallets`() {
        val fWal = Wallet()
        fWal.balance = 400
        fWal.bottomDivider = 200

        val sWal = Wallet()
        sWal.balance = 200

        val tWal = Wallet()
        tWal.balance = 150
        tWal.bottomDivider = 100

        val foWal = Wallet()
        foWal.balance = 50

        `prepare Calculator`(
            userInput = 500,
            userConfigWallet = hashMapOf(Pair(fWal, 300), Pair(sWal, 200)),
            otherWallets = listOf(tWal, foWal)
        )

        val result = removeCalc.getCalculatedResult()

        assertResultListBalances(intArrayOf(0, 0, 100, 200), result.map { it.balance }.sorted().toIntArray())
    }

    @Test
    fun `assert negative balance on userConfig`() {
        val wallet = Wallet()
        wallet.balance = 200
        `prepare Calculator`(
            userInput = 300,
            userConfigWallet = hashMapOf(Pair(wallet, 300)),
            otherWallets = emptyList()
        )

        val result = removeCalc.getCalculatedResult()

        assertResultListBalances(intArrayOf(-100), result.map { it.balance }.sorted().toIntArray())
    }

    @Test
    fun `assert negative balance on others`() {
        val wallet = Wallet()
        wallet.balance = 200
        `prepare Calculator`(
            userInput = 300,
            userConfigWallet = hashMapOf(),
            otherWallets = listOf(wallet)
        )

        val result = removeCalc.getCalculatedResult()

        assertResultListBalances(intArrayOf(-100), result.map { it.balance }.sorted().toIntArray())
    }


}