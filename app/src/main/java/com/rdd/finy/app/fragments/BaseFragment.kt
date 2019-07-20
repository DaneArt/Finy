package com.rdd.finy.app.fragments

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpAppCompatFragment

open class BaseFragment : MvpAppCompatFragment() {

    private val disposables = CompositeDisposable()

    fun attachDisposable(d: Disposable){
        disposables.add(d)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}