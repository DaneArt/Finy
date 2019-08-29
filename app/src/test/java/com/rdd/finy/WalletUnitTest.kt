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