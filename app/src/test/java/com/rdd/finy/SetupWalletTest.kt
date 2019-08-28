package com.rdd.finy

import com.rdd.finy.app.models.Wallet
import com.rdd.finy.app.presenters.SetupWalletPresenter
import com.rdd.finy.data.repositories.WalletRepositoryImpl
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock

class SetupWalletTest {

    private val repository: WalletRepositoryImpl = mock(WalletRepositoryImpl::class.java)
    private val presenter = SetupWalletPresenter(repository)

    @Test
    fun `test converter without kopeykas`() {
        val input = "500"

        val result = presenter.getSaveableMoney(input)

        assertEquals(50000, result)
    }

    @Test
    fun `test converter with kopeykas`() {
        val input = "500.5"

        val result = presenter.getSaveableMoney(input)

        assertEquals(50005, result)
    }

    @Test
    fun `test converter with kopeykas equals zero`() {
        val input = "500."

        val result = presenter.getSaveableMoney(input)

        assertEquals(50000, result)
    }

    @Test
    fun `test converter with kopeykas higher than 100`() {
        val input = "500.897"

        val result = presenter.getSaveableMoney(input)

        assertEquals(50089, result)
    }

    @Test
    fun test() {
        val wallet = Wallet()
        wallet.balance = 14
    }
}