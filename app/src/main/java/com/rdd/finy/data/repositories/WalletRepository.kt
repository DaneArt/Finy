package com.rdd.finy.data.repositories

import com.rdd.finy.app.models.Wallet
import io.reactivex.Flowable

interface WalletRepository : Repository<Wallet> {
    fun getAllWalletsBalances(): Flowable<LongArray>

    fun getActiveWalletsBalances(): Flowable<LongArray>
}