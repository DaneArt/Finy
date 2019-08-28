package com.rdd.finy

import com.rdd.finy.app.models.Wallet
import com.rdd.finy.helpers.AddCalculator
import com.rdd.finy.helpers.CalculatorBeverage
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AddCalculatorTest {

    private lateinit var addCalc: CalculatorBeverage

    private fun `prepare Calculator`(
            userInput: Long,
            userConfigWallet: HashMap<Wallet, Long>,
            otherWallets: List<Wallet>
    ) {
        addCalc = AddCalculator(
            userBalance = userInput, userConfigWallets = userConfigWallet,
            otherWallets = otherWallets
        )
    }

    private fun `prepare Calculator only with ideal userConfig wallets`() {
        val usersWallets = hashMapOf<Wallet, Long>()
        usersWallets[Wallet(id = 1)] = 200
        usersWallets[Wallet(id = 2)] = 300
        `prepare Calculator`(userInput = 500, userConfigWallet = usersWallets, otherWallets = emptyList())
    }

    private fun `prepare Calculator only with ideal other wallets`() {
        val others = arrayListOf<Wallet>()
        others.add(Wallet(id = 1))
        others.add(Wallet(id = 2))
        `prepare Calculator`(userInput = 500, userConfigWallet = hashMapOf(), otherWallets = others)
    }

    private fun `prepare Calculator with ideal both walletLists`() {
        val usersWallets = hashMapOf<Wallet, Long>()
        usersWallets[Wallet(id = 1)] = 200
        usersWallets[Wallet(id = 2)] = 300
        val others = arrayListOf<Wallet>()
        others.add(Wallet(id = 1))
        others.add(Wallet(id = 2))
        `prepare Calculator`(userInput = 1000, userConfigWallet = usersWallets, otherWallets = others)
    }

    private fun assertResultListSize(expectedSize: Int, actualSize: Int) {
        assertEquals(expectedSize, actualSize)
    }

    private fun assertResultListBalances(expectedBalanceArray: LongArray, actualBalanceArray: LongArray) {

        assertEquals(true, expectedBalanceArray.contentEquals(actualBalanceArray))
    }

    @Test
    fun `check that user input is fully used with ideal userConfig wallets`() {
        `prepare Calculator only with ideal userConfig wallets`()

        val resultList = addCalc.getCalculatedResult()
        val resultUserInput = addCalc.userBalance

        assertEquals(0, resultUserInput)
    }

    @Test
    fun `assert that result list size is expected with ideal userConfig wallets`() {
        `prepare Calculator only with ideal userConfig wallets`()

        val resultList = addCalc.getCalculatedResult()

        assertResultListSize(2, resultList.size)
    }

    @Test
    fun `assert that result list has correct items with ideal userConfig wallets`() {
        `prepare Calculator only with ideal userConfig wallets`()

        val resultList = addCalc.getCalculatedResult()

        assertResultListBalances(longArrayOf(200, 300), resultList.map { it.balance }.sorted().toLongArray())
    }

    @Test
    fun `check that user input is fully used with ideal other wallets`() {
        `prepare Calculator only with ideal other wallets`()

        val resultList = addCalc.getCalculatedResult()
        val resultUserInput = addCalc.userBalance

        assertEquals(0, resultUserInput)
    }

    @Test
    fun `assert that result list size is expected with ideal other wallets`() {
        `prepare Calculator only with ideal other wallets`()

        val resultList = addCalc.getCalculatedResult()

        assertResultListSize(2, resultList.size)
    }

    @Test
    fun `assert that result list has correct items with ideal other wallets`() {
        `prepare Calculator only with ideal other wallets`()

        val resultList = addCalc.getCalculatedResult()

        assertResultListBalances(longArrayOf(250, 250), resultList.map { it.balance }.toLongArray())
    }

    @Test
    fun `check that user input is fully used with ideal both wallets`() {
        `prepare Calculator with ideal both walletLists`()

        addCalc.getCalculatedResult()
        val resultUserInput = addCalc.userBalance

        assertEquals(0, resultUserInput)
    }

    @Test
    fun `assert that result list size is expected with ideal both wallets`() {
        `prepare Calculator with ideal both walletLists`()

        val resultList = addCalc.getCalculatedResult()

        assertResultListSize(4, resultList.size)
    }

    @Test
    fun `assert that result list has correct items with ideal both wallets`() {
        `prepare Calculator with ideal both walletLists`()

        val resultList = addCalc.getCalculatedResult()

        assertResultListBalances(longArrayOf(200, 250, 250, 300), resultList.map { it.balance }.sorted().toLongArray())
    }

    @Test
    fun `assert that extraWallet was Created with empty walletsList`() {
        `prepare Calculator`(userInput = 500, userConfigWallet = hashMapOf(), otherWallets = emptyList())

        val result = addCalc.getCalculatedResult()

        assertEquals(1, result.size)
        assertEquals("Extra", result[0].title)
        assertEquals(500, result[0].balance)
    }

    @Test
    fun `assert that extraWallet was Created with divider on userConfig wallets`() {
        val testWallet = Wallet()
        testWallet.upperDivider = 200
        `prepare Calculator`(
            userInput = 500,
                userConfigWallet = hashMapOf(Pair(testWallet, 300.toLong())),
            otherWallets = emptyList()
        )

        val result = addCalc.getCalculatedResult()

        assertEquals(2, result.size)
        assertEquals("Extra", result[1].title)
        assertEquals(300, result[1].balance)
    }

    @Test
    fun `assert that extraWallet was Created with divider on other wallets`() {
        val testWallet = Wallet()
        testWallet.upperDivider = 200
        `prepare Calculator`(
            userInput = 500,
            userConfigWallet = hashMapOf(),
            otherWallets = listOf(testWallet)
        )

        val result = addCalc.getCalculatedResult()

        assertEquals(2, result.size)
        assertEquals("Extra", result[1].title)
        assertEquals(300, result[1].balance)
    }

    @Test
    fun `assert that extraWallet was Created with divider on both wallets lists`() {
        val testUserWallet = Wallet()
        testUserWallet.upperDivider = 200
        val testOtherWallet = Wallet()
        testOtherWallet.upperDivider = 200
        `prepare Calculator`(
            userInput = 500,
                userConfigWallet = hashMapOf(Pair(testUserWallet, 300.toLong())),
            otherWallets = listOf(testOtherWallet)
        )

        val result = addCalc.getCalculatedResult()

        assertEquals(3, result.size)
        assertEquals("Extra", result[2].title)
        assertEquals(100, result[2].balance)
    }

    @Test
    fun `assert that money was correctly distributed with divider on userConfig wallets`() {
        val testUserWallet = Wallet()
        testUserWallet.upperDivider = 200
        `prepare Calculator`(
            userInput = 500,
                userConfigWallet = hashMapOf(Pair(testUserWallet, 300.toLong()), Pair(Wallet(), 400.toLong())),
            otherWallets = emptyList()
        )

        val result = addCalc.getCalculatedResult()

        assertResultListBalances(longArrayOf(200, 300), result.map { it.balance }.sorted().toLongArray())
    }

    @Test
    fun `assert that money was correctly distributed with divider on other wallets`() {
        val testWallet = Wallet()
        testWallet.upperDivider = 200
        `prepare Calculator`(
            userInput = 500,
            userConfigWallet = hashMapOf(),
            otherWallets = listOf(testWallet, Wallet())
        )

        val result = addCalc.getCalculatedResult()

        assertResultListBalances(longArrayOf(200, 300), result.map { it.balance }.sorted().toLongArray())
    }

    @Test
    fun `assert that money was correctly distributed with divider on both wallets`() {
        val testWallet = Wallet()
        testWallet.upperDivider = 200
        val testUserWallet = Wallet()
        testUserWallet.upperDivider = 100
        `prepare Calculator`(
            userInput = 500,
                userConfigWallet = hashMapOf(Pair(testUserWallet, 300.toLong()), Pair(Wallet(), 300.toLong())),
            otherWallets = listOf(testWallet, Wallet())
        )

        val result = addCalc.getCalculatedResult()

        assertResultListBalances(longArrayOf(50, 50, 100, 300), result.map { it.balance }.sorted().toLongArray())
    }
}