package com.rdd.finy.activities

import android.os.Bundle
import android.support.v4.view.ViewPager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.rdd.finy.R
import com.rdd.finy.adapters.WalletsAdapter
import com.rdd.finy.data.Wallet
import com.rdd.finy.helpers.FinyApp
import com.rdd.finy.presenters.InfoPresenter
import com.rdd.finy.views.InfoView

class InfoActivity : MvpAppCompatActivity(),InfoView {

    @InjectPresenter
    lateinit var infoPresenter: InfoPresenter

    private lateinit var walletsPager : ViewPager
    private lateinit var walletsAdapter: WalletsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        walletsPager = findViewById(R.id.wallets_VPager)

        FinyApp.app()?.appComponent()?.inject(this)

        infoPresenter.testload()

        walletsAdapter = WalletsAdapter(supportFragmentManager)

        walletsPager.adapter = walletsAdapter

    }

    override fun setupEmptyWalletsList() {
        walletsAdapter.setupWallets(emptyList())

    }

    override fun setupWalletsList(wallets: List<Wallet>) {
        walletsAdapter.setupWallets(wallets)

    }
}
