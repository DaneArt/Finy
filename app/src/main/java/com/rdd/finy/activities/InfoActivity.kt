package com.rdd.finy.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

class InfoActivity : MvpAppCompatActivity(), InfoView, WalletsAdapter.Callbacks {

    private val DIALOG_CONTROL_MONEY = "controlMoneyDialog"

    private val TAG = InfoActivity::class.java.simpleName

    init {
        App.app()?.appComponent()?.inject(this@InfoActivity)
    }

    @Inject
    lateinit var walletRepository: WalletRepository

    @InjectPresenter
    lateinit var infoPresenter: InfoPresenter

    @ProvidePresenter
    fun provideInfoPresenter(): InfoPresenter {
        return InfoPresenter(walletRepository)
    }

    companion object {
        const val DIALOG_WALLET_STATE = "walletState"
    }

    private lateinit var controlMoneyMainFab: FloatingActionButton
    private lateinit var controlMoneyRemoveFab: FloatingActionButton
    private lateinit var controlMoneyInsertFab: FloatingActionButton
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private var FAB_status = false

    private lateinit var show_remove_fab: Animation
    private lateinit var hide_remove_fab: Animation
    private lateinit var show_main_fab: Animation
    private lateinit var hide_main_fab: Animation
    private lateinit var show_insert_fab: Animation
    private lateinit var hide_insert_fab: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        controlMoneyMainFab = findViewById(R.id.fab_control_main)
        controlMoneyRemoveFab = findViewById(R.id.fab_control_remove)
        controlMoneyInsertFab = findViewById(R.id.fab_control_insert)
        toolbar = findViewById(R.id.toolbar_info)

        show_remove_fab = AnimationUtils.loadAnimation(applicationContext, R.anim.remove_fab_show)
        hide_remove_fab = AnimationUtils.loadAnimation(applicationContext, R.anim.remove_fab_hide)
        show_main_fab = AnimationUtils.loadAnimation(applicationContext, R.anim.main_fab_show)
        hide_main_fab = AnimationUtils.loadAnimation(applicationContext, R.anim.main_fab_hide)
        show_insert_fab = AnimationUtils.loadAnimation(applicationContext, R.anim.insert_fab_show)
        hide_insert_fab = AnimationUtils.loadAnimation(applicationContext, R.anim.insert_fab_hide)

        setSupportActionBar(toolbar)

        controlMoneyMainFab.setOnClickListener {
            FAB_status = if (!FAB_status) {
                expandFAB()
                true
            } else {
                hideFAB()
                false
            }
        }

        controlMoneyRemoveFab.setOnClickListener {
            infoPresenter.removeMoney()
            Log.e(TAG, "Remove clicked")
        }

        controlMoneyInsertFab.setOnClickListener {
            infoPresenter.addMoney()
            Log.e(TAG, "Insert clicked")
        }

        infoPresenter.setupTotalBalance()

        val manager = supportFragmentManager
        val fragment = WalletsContainerFragment()
        manager.beginTransaction()
                .add(R.id.container_wallets_fragment, fragment)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_wallet, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.btn_menu_create_wallet -> {
                val manager = supportFragmentManager
                val dialog = SetupWalletDialog()
                dialog.show(manager, DIALOG_WALLET_STATE)
            }
        }
        return true
    }

    override fun expandFAB() {
        val removeParams: FrameLayout.LayoutParams = controlMoneyRemoveFab.layoutParams as FrameLayout.LayoutParams
        removeParams.bottomMargin += (controlMoneyRemoveFab.height * 1.7).toInt()
        controlMoneyRemoveFab.layoutParams = removeParams
        controlMoneyRemoveFab.startAnimation(show_remove_fab)
        controlMoneyRemoveFab.isClickable = true
        Log.e(TAG, "Remove is ${controlMoneyRemoveFab.isEnabled}")

        val insertParams: FrameLayout.LayoutParams = controlMoneyInsertFab.layoutParams as FrameLayout.LayoutParams
        insertParams.rightMargin += (controlMoneyInsertFab.width * 1.7).toInt()
        controlMoneyInsertFab.layoutParams = insertParams
        controlMoneyInsertFab.startAnimation(show_insert_fab)
        controlMoneyInsertFab.isClickable = true
        Log.e(TAG, "Insert is ${controlMoneyInsertFab.isEnabled}")

        controlMoneyMainFab.setImageResource(R.drawable.ic_control_money_turn_of)
        controlMoneyMainFab.startAnimation(show_main_fab)
    }

    override fun hideFAB() {
        val removeParams: FrameLayout.LayoutParams = controlMoneyRemoveFab.layoutParams as FrameLayout.LayoutParams
        removeParams.bottomMargin -= (controlMoneyRemoveFab.height * 1.7).toInt()
        controlMoneyRemoveFab.layoutParams = removeParams
        controlMoneyRemoveFab.startAnimation(hide_remove_fab)
        controlMoneyRemoveFab.isClickable = false
        Log.e(TAG, "Remove is ${controlMoneyRemoveFab.isEnabled}")


        val insertParams: FrameLayout.LayoutParams = controlMoneyInsertFab.layoutParams as FrameLayout.LayoutParams
        insertParams.rightMargin -= (controlMoneyInsertFab.width * 1.7).toInt()
        controlMoneyInsertFab.layoutParams = insertParams
        controlMoneyInsertFab.startAnimation(hide_insert_fab)
        controlMoneyInsertFab.isClickable = false
        Log.e(TAG, "Insert is ${controlMoneyInsertFab.isEnabled}")

        controlMoneyMainFab.setImageResource(R.drawable.ic_control_money_turn_on)
        controlMoneyMainFab.startAnimation(hide_main_fab)
    }

    override fun showControlMoneyDialog(isAddingMoney: Boolean) {
        val manager = supportFragmentManager
        val dialog = ControlMoneyDialog.newInstance(isAddingMoney)
        dialog.show(manager, DIALOG_CONTROL_MONEY)
    }

    override fun updateTotalBalanceState(totalBalance: Double) {
        supportActionBar?.title = getString(R.string.total_balance_menu, totalBalance)
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
