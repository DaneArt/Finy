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
package com.rdd.finy.app.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdd.finy.App
import com.rdd.finy.R
import com.rdd.finy.app.adapters.WalletsAdapter
import com.rdd.finy.app.models.Wallet
import com.rdd.finy.app.presenters.WalletsContainerPresenter
import com.rdd.finy.app.views.WalletsContainerView
import com.rdd.finy.data.repositories.WalletRepositoryImpl
import io.reactivex.disposables.Disposable
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class WalletsContainerFragment : BaseFragment(), WalletsContainerView {

    private val TAG = WalletsContainerFragment::class.java.simpleName

    init {
        App.app()?.appComponent()?.inject(this@WalletsContainerFragment)
    }

    @Inject
    lateinit var walletRepositoryImpl: WalletRepositoryImpl

    @InjectPresenter
    lateinit var walletsContainerPresenter: WalletsContainerPresenter

    @ProvidePresenter
    fun provideWalletsContainerPresenter(): WalletsContainerPresenter {
        return WalletsContainerPresenter(walletRepositoryImpl)
    }

    private lateinit var walletsRView: RecyclerView
    private lateinit var walletsAdapter: WalletsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_wallets_container, container, false)

        walletsRView = view.findViewById(R.id.rv_wallets)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orientation = context?.resources?.configuration?.orientation

        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            walletsRView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            walletsRView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        walletsAdapter = WalletsAdapter(context!!)
        walletsRView.adapter = walletsAdapter

        walletsContainerPresenter.loadWalletsFromDB()
    }

    override fun attachNewDisposable(d: Disposable) {
        attachDisposable(d)
    }

    override fun setupEmptyWalletsList() {
        walletsAdapter.setupWalletsList(emptyList())
    }

    override fun setupWalletsList(wallets: List<Wallet>) {

        walletsAdapter.setupWalletsList(wallets = wallets)
    }

    override fun updateCurrentWallet(wallet: Wallet) {
        walletsAdapter.updateWalletInList(wallet)
    }

    override fun removeCurrentWallet(wallet: Wallet) {
        walletsAdapter.removeWalletFromList(wallet.id)
    }

    override fun insertCurrentWallet(wallet: Wallet) {
        walletsAdapter.addWalletToList(wallet)
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}