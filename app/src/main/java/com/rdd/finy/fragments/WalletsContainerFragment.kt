package com.rdd.finy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdd.finy.App
import com.rdd.finy.R
import com.rdd.finy.adapters.WalletsAdapter
import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletRepository
import com.rdd.finy.presenters.WalletsContainerPresenter
import com.rdd.finy.views.WalletsContainerView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class WalletsContainerFragment : MvpAppCompatFragment(), WalletsContainerView {


    private val TAG = WalletsContainerFragment::class.java.simpleName

    init {
        App.app()?.appComponent()?.inject(this@WalletsContainerFragment)
    }

    @Inject
    lateinit var walletRepository: WalletRepository

    @InjectPresenter
    lateinit var walletsContainerPresenter: WalletsContainerPresenter

    @ProvidePresenter
    fun provideWalletsContainerPresenter():WalletsContainerPresenter{
        return WalletsContainerPresenter(walletRepository)
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

        walletsRView.layoutManager = GridLayoutManager(context, 2)

        walletsAdapter = WalletsAdapter(context!!)
        walletsRView.adapter = walletsAdapter

        walletsContainerPresenter.loadWalletsFromDB()
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

    override fun onDestroy() {
        super.onDestroy()
        walletsContainerPresenter.disposeWalletsList()
    }
}