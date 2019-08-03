package com.rdd.finy

import com.rdd.finy.app.models.Wallet
import junit.framework.Assert.*
import org.junit.Test


class WalletUnitTest {

    private lateinit var wallet: Wallet

    private fun setUpWallet(){
        wallet = Wallet()
    }

    @Test
    fun `Balance exist with empty constructor`() {
        setUpWallet()

        assertNotNull(wallet.balance)
    }

    @Test
    fun `Upper divider not exists with empty constructor`() {
        setUpWallet()

        assertNull(wallet.upperDivider)
    }

    @Test
    fun `Bottom divider not exist with empty constructor`() {
        setUpWallet()

        assertNull(wallet.bottomDivider)
    }

    @Test
    fun `try to set ideal balance`() {
        setUpWallet()

        wallet.upperDivider = 20
        wallet.balance = 15
        wallet.bottomDivider = 10

        assertEquals(15, wallet.balance)
    }

    @Test
    @Throws(Exception::class)
    fun `try to set balance higher than upper divider`() {
        setUpWallet()
        wallet.upperDivider = 20

        try {
            wallet.balance = 65
            fail("Expected Exception")
        } catch (e: Exception) {
            assertEquals("Upper divider should be more or equals balance", e.message)
        }

    }

    @Test
    @Throws(Exception::class)
    fun `try to set balance lower than bottom divider`() {
        setUpWallet()
        wallet.balance = 10
        wallet.bottomDivider = 6

        try {
            wallet.balance = 4
            fail("Expected Exception")
        } catch (e: Exception) {
            assertEquals("Bottom divider should be less or equals balance", e.message)
        }

    }

    @Test
    @Throws(Exception::class)
    fun `try to set bottom divider higher than balance`() {
        setUpWallet()
        wallet.balance = 4

        try {
            wallet.bottomDivider = 6
            fail("Expected Exception")
        } catch (e: Exception) {
            assertEquals("Bottom divider should be less than upper and balance", e.message)
        }
    }

    @Test
    @Throws(Exception::class)
    fun `try to set bottom divider higher than upper divider`() {
        setUpWallet()
        wallet.upperDivider = 4

        try {
            wallet.bottomDivider = 6
            fail("Expected Exception")
        } catch (e: Exception) {
            assertEquals("Bottom divider should be less than upper and balance", e.message)
        }
    }

    @Test
    @Throws(Exception::class)
    fun `try to set upper divider lower than balance`() {
        setUpWallet()
        wallet.balance = 5

        try {
            wallet.upperDivider = 2
            fail("Expected Exception")
        } catch (e: Exception) {
            assertEquals("Upper divider should be more than bottom and balance", e.message)
        }
    }

    @Test
    @Throws(Exception::class)
    fun `try to set upper divider lower than bottom divider`() {
        setUpWallet()
        wallet.balance = 5
        wallet.bottomDivider = 3

        try {
            wallet.upperDivider = 2
            fail("Expected Exception")
        } catch (e: Exception) {
            assertEquals("Upper divider should be more than bottom and balance", e.message)
        }
    }
}