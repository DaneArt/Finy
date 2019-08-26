package com.rdd.finy.app.presenters

import com.rdd.finy.App
import com.rdd.finy.app.views.ControlMoneyView
import com.rdd.finy.data.repositories.WalletRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ControlMoneyPresenter : MvpPresenter<ControlMoneyView>() {

    @Inject
    lateinit var walletRepositoryImpl: WalletRepositoryImpl

    init {
        App.app()?.appComponent()?.inject(this)
    }

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun loadWalletsFromDB() {

        disposables.add(
            walletRepositoryImpl.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setupSourceList(it)
                }, {

                })
        )
    }

    fun attachDisposable(d: Disposable) {
        disposables.add(d)
    }

    fun destroyDisposable() {
        disposables.clear()
    }
}