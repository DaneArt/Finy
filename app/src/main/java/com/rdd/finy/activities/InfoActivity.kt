package com.rdd.finy.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import moxy.presenter.ProvidePresenter
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

    @ProvidePresenter
    fun provideInfoPresenter():InfoPresenter{
        return InfoPresenter(walletRepository)
    }

    companion object{
        const val DIALOG_WALLET_STATE = "walletState"
    }

    private lateinit var addMoneyBtn : Button
    private lateinit var removeMoneyBtn : Button
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        addMoneyBtn = findViewById(R.id.btn_show_insert_money_dialog)
        removeMoneyBtn = findViewById(R.id.btn_show_remove_money_dialog)
        toolbar = findViewById(R.id.toolbar_info)

        setSupportActionBar(toolbar)

        addMoneyBtn.setOnClickListener {
            infoPresenter.addMoney()
        }

        removeMoneyBtn.setOnClickListener {
            infoPresenter.removeMoney()
        }

        infoPresenter.setupTotalBalance()

        val manager = supportFragmentManager
        val fragment = WalletsContainerFragment()
        manager.beginTransaction()
            .add(R.id.container_wallets_fragment,fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_wallet,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.btn_menu_create_wallet ->{
                val manager = supportFragmentManager
                val dialog = SetupWalletDialog()
                dialog.show(manager, DIALOG_WALLET_STATE)
            }
        }
        return true
    }

    override fun showControlMoneyDialog(isAddingMoney : Boolean) {
        val manager = supportFragmentManager
        val dialog = ControlMoneyDialog.newInstance(isAddingMoney)
        dialog.show(manager, DIALOG_CONTROL_MONEY)
    }

    override fun updateTotalBalanceState(totalBalance: Double) {
        supportActionBar?.title = getString(R.string.total_balance_menu,totalBalance)
    }

    override fun onShowSetupWalletDialog(walletId: Long) {
        val manager = supportFragmentManager
        val dialog = SetupWalletDialog.newInstance(walletId)
        dialog.show(manager, DIALOG_WALLET_STATE)
    }

    override fun onUpdateWallet(wallet: Wallet) {
        walletRepository.updateWallet(wallet)
    }

}
