package com.rdd.finy.presenters


import android.util.Log
import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletRepository
import com.rdd.finy.views.WalletsContainerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.*

@InjectViewState
class WalletsContainerPresenter(private val walletRepository: WalletRepository) : MvpPresenter<WalletsContainerView>() {

    private val TAG = WalletsContainerPresenter::class.java.simpleName

    private var localIdsList: List<Long> = listOf()
    private var localWalletsList: List<Wallet> = listOf()
    private lateinit var walletsDisposable: Disposable

    fun loadWalletsFromDB() {

        walletsDisposable = walletRepository.getAllWallets()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { walletsFromDB ->
                    Log.e(TAG, Arrays.toString(localIdsList.toLongArray()))
                    when {
                        localIdsList.isEmpty() -> {
                            viewState.setupWalletsList(walletsFromDB)
                        }
                        walletsFromDB.size > localIdsList.size -> {
                            viewState.insertCurrentWallet(walletsFromDB[walletsFromDB.size-1])
                        }
                        walletsFromDB.size < localIdsList.size-> {
                            for(wallet in localWalletsList){
                                if(!walletsFromDB.contains(wallet)) {
                                    viewState.removeCurrentWallet(wallet)
                                }
                            }
                        }
                        walletsFromDB.size == localIdsList.size -> {
                            for(wallet in localWalletsList){
                                if(!walletsFromDB.contains(wallet)) {
                                    val index = localWalletsList.indexOf(wallet)
                                    viewState.updateCurrentWallet(walletsFromDB[index])
                                }
                            }
                        }
                    }
                    localIdsList = walletsFromDB.map { it.id }
                    localWalletsList = walletsFromDB
                },
                { error ->
                    Log.e(TAG, "Exception: ${error.localizedMessage}")
                }
            )
    }


    fun disposeWalletsList() {
        walletsDisposable.dispose()
    }


}