package com.rdd.finy

import com.rdd.finy.app.models.Wallet
import junit.framework.Assert.assertEquals
import org.junit.Test

class WalletUnitTest {

    private lateinit var wallet: Wallet

    private fun setUpWallet(){
        wallet = Wallet()
    }

    @Test
    fun testCantSaveWithUpperDivider(){
        setUpWallet()
        wallet.balance = 50.0
        wallet.upperDivider = 30.0
        assertEquals(false, wallet.couldBeSaved)
    }

    @Test
    fun testCantSaveWithBottomDivider(){
        setUpWallet()
        wallet.balance = 30.0
        wallet.bottomDivider = 50.0
        assertEquals(false, wallet.couldBeSaved)
    }

    @Test
    fun testCouldSave(){
        setUpWallet()
        assertEquals(true,wallet.couldBeSaved)
    }

    @Test
    fun testUpperDivider(){
        setUpWallet()
        assertEquals(false,wallet.hasUpperDivider)
        wallet.upperDivider = 100.0
        assertEquals(true,wallet.hasUpperDivider)
    }

    @Test
    fun testBottomDivider(){
        setUpWallet()
        assertEquals(false, wallet.hasBottomDivider)
        wallet.bottomDivider = 10.0
        assertEquals(true,wallet.hasBottomDivider)
    }

}