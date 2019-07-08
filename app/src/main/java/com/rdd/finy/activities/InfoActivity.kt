package com.rdd.finy.activities

import android.os.Bundle
import android.widget.Button
import com.rdd.finy.App
import com.rdd.finy.R
import com.rdd.finy.adapters.WalletsAdapter
import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletRepository
import com.rdd.finy.fragments.ControlMoneyDialog
import com.rdd.finy.fragments.SetupWalletDialog
import com.rdd.finy.fragments.WalletsContainerFragment
import com.rdd.finy.presenters.InfoPresenter
import com.rdd.finy.views.InfoView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import javax.inject.Inject

class InfoActivity : MvpAppCompatActivity(), InfoView,WalletsAdapter.Callbacks {

    private val DIALOG_CONTROL_MONEY = "controlMoneyDialog"

    init {
        App.app()?.appComponent()?.inject(this@InfoActivity)
    }

    @Inject
    lateinit var walletRepository: WalletRepository

    @InjectPresenter
    lateinit var infoPresenter: InfoPresenter

    private lateinit var addMoneyBtn : Button
    private lateinit var removeMoneyBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        addMoneyBtn = findViewById(R.id.btn_show_insert_money_dialog)
        removeMoneyBtn = findViewById(R.id.btn_show_remove_money_dialog)

        addMoneyBtn.setOnClickListener {
            infoPresenter.addMoney()
        }

        removeMoneyBtn.setOnClickListener {
            infoPresenter.removeMoney()
        }

        val manager = supportFragmentManager
        val fragment = WalletsContainerFragment()
        manager.beginTransaction()
            .add(R.id.container_wallets_fragment,fragment)
            .commit()
    }

    override fun showControlMoneyDialog(isAddingMoney : Boolean) {
        val manager = supportFragmentManager
        val dialog = ControlMoneyDialog.newInstance(isAddingMoney)
        dialog.show(manager, DIALOG_CONTROL_MONEY)
    }

    override fun onShowSetupWalletDialog(walletId: Long) {
        val manager = supportFragmentManager
        val dialog = SetupWalletDialog.newInstance(walletId)
        dialog.show(manager, WalletsContainerFragment.DIALOG_WALLET_STATE)
    }


    override fun onUpdateWallet(wallet: Wallet) {
        walletRepository.updateWallet(wallet)
    }

}
