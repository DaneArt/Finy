package com.rdd.finy.app.presenters

import com.rdd.finy.app.models.Wallet
import com.rdd.finy.app.views.SetupWalletDialogView
import com.rdd.finy.data.repositories.WalletRepositoryImpl
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class SetupWalletPresenter(private val walletRepositoryImpl: WalletRepositoryImpl) :
    MvpPresenter<SetupWalletDialogView>() {

    fun setupEditViewWithWalletFromDB(walletId: Long) {
        walletRepositoryImpl.getById(walletId)
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