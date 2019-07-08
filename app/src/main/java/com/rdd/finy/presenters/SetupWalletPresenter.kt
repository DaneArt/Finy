package com.rdd.finy.presenters

import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletRepository
import com.rdd.finy.views.SetupWalletDialogView
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class SetupWalletPresenter(private val walletRepository: WalletRepository) : MvpPresenter<SetupWalletDialogView>() {

    fun setupEditViewWithWalletFromDB(walletId: Long) {
        walletRepository.getWalletById(walletId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Wallet> {
                override fun onSuccess(wallet: Wallet) {
                    viewState.setupEditView(wallet)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(error: Throwable) {
                }
            })
    }

}