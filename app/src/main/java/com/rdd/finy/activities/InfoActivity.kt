package com.rdd.finy.activities

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.rdd.finy.R
import com.rdd.finy.adapters.WalletsAdapter
import com.rdd.finy.data.Wallet
import com.rdd.finy.presenters.InfoPresenter
import com.rdd.finy.views.InfoView
import kotlin.math.log

class InfoActivity : MvpAppCompatActivity(), InfoView {

    @InjectPresenter
    lateinit var infoPresenter: InfoPresenter

    private lateinit var walletsPager: ViewPager
    private lateinit var walletsAdapter: WalletsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        walletsPager = findViewById(R.id.wallets_VPager)

        infoPresenter.loadWallets()

        walletsAdapter = WalletsAdapter(supportFragmentManager)

        walletsPager.adapter = walletsAdapter

    }

    override fun setupEmptyWalletsList() {
        walletsAdapter.setupWallets(emptyList())
    }

    override fun setupWalletsList(wallets: List<Wallet>) {
        walletsAdapter.setupWallets(wallets)
    }

    override fun setToNewWallet(lastPos : Int) {
        walletsPager.currentItem = lastPos
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_wallet, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.menu_addWallet ){
            infoPresenter.addWallet()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
