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