package com.rdd.finy.app.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rdd.finy.App
import com.rdd.finy.R
import com.rdd.finy.app.adapters.WalletsAdapter
import com.rdd.finy.app.fragments.ControlMoneyDialog
import com.rdd.finy.app.fragments.SetupWalletDialog
import com.rdd.finy.app.fragments.StatsContainerFragment
import com.rdd.finy.app.fragments.WalletsContainerFragment
import com.rdd.finy.app.models.Wallet
import com.rdd.finy.app.presenters.InfoPresenter
import com.rdd.finy.app.views.InfoView
import com.rdd.finy.data.repositories.WalletRepository
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

    @BindView(R.id.fab_control_main)
    lateinit var controlMoneyMainFab: FloatingActionButton
    @BindView(R.id.fab_control_remove)
    lateinit var controlMoneyRemoveFab: FloatingActionButton
    @BindView(R.id.fab_control_insert)
    lateinit var controlMoneyInsertFab: FloatingActionButton

    private var statusFAB = false

    private lateinit var showRemoveFab: Animation
    private lateinit var hideRemoveFab: Animation
    private lateinit var showMainFab: Animation
    private lateinit var hideMainFab: Animation
    private lateinit var showInsertFab: Animation
    private lateinit var hideInsertFab: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        ButterKnife.bind(this)

        prepareAnims()

        infoPresenter.loadWalletsData()

        val manager = supportFragmentManager
        val walletsFragment = WalletsContainerFragment()
        val statsFragment = StatsContainerFragment()
        manager.beginTransaction()
                .add(R.id.container_wallets_fragment, walletsFragment)
                .add(R.id.container_stats_fragment, statsFragment)
                .commit()

    }

    @OnClick(R.id.fab_control_main)
    override fun changeFabsViewState() {
        statusFAB = if (!statusFAB) {
            expandFAB()
            true
        } else {
            hideFAB()
            false
        }
    }

    @OnClick(R.id.fab_control_insert)
    override fun insertMoney() {
        infoPresenter.addMoney()
    }

    @OnClick(R.id.fab_control_remove)
    override fun removeMoney() {
        infoPresenter.removeMoney()
    }

    override fun prepareAnims() {
        showRemoveFab = AnimationUtils.loadAnimation(applicationContext, R.anim.remove_fab_show)
        hideRemoveFab = AnimationUtils.loadAnimation(applicationContext, R.anim.remove_fab_hide)
        showMainFab = AnimationUtils.loadAnimation(applicationContext, R.anim.main_fab_show)
        hideMainFab = AnimationUtils.loadAnimation(applicationContext, R.anim.main_fab_hide)
        showInsertFab = AnimationUtils.loadAnimation(applicationContext, R.anim.insert_fab_show)
        hideInsertFab = AnimationUtils.loadAnimation(applicationContext, R.anim.insert_fab_hide)
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
        controlMoneyRemoveFab.startAnimation(showRemoveFab)
        controlMoneyRemoveFab.isClickable = true
        Log.e(TAG, "Remove is ${controlMoneyRemoveFab.isEnabled}")

        val insertParams: FrameLayout.LayoutParams = controlMoneyInsertFab.layoutParams as FrameLayout.LayoutParams
        insertParams.rightMargin += (controlMoneyInsertFab.width * 1.7).toInt()
        controlMoneyInsertFab.layoutParams = insertParams
        controlMoneyInsertFab.startAnimation(showInsertFab)
        controlMoneyInsertFab.isClickable = true
        Log.e(TAG, "Insert is ${controlMoneyInsertFab.isEnabled}")

        controlMoneyMainFab.setImageResource(R.drawable.ic_control_money_turn_of)
        controlMoneyMainFab.startAnimation(showMainFab)
    }

    override fun hideFAB() {
        val removeParams: FrameLayout.LayoutParams = controlMoneyRemoveFab.layoutParams as FrameLayout.LayoutParams
        removeParams.bottomMargin -= (controlMoneyRemoveFab.height * 1.7).toInt()
        controlMoneyRemoveFab.layoutParams = removeParams
        controlMoneyRemoveFab.startAnimation(hideRemoveFab)
        controlMoneyRemoveFab.isClickable = false
        Log.e(TAG, "Remove is ${controlMoneyRemoveFab.isEnabled}")

        val insertParams: FrameLayout.LayoutParams = controlMoneyInsertFab.layoutParams as FrameLayout.LayoutParams
        insertParams.rightMargin -= (controlMoneyInsertFab.width * 1.7).toInt()
        controlMoneyInsertFab.layoutParams = insertParams
        controlMoneyInsertFab.startAnimation(hideInsertFab)
        controlMoneyInsertFab.isClickable = false
        Log.e(TAG, "Insert is ${controlMoneyInsertFab.isEnabled}")

        controlMoneyMainFab.setImageResource(R.drawable.ic_control_money_turn_on)
        controlMoneyMainFab.startAnimation(hideMainFab)
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

    override fun onDestroy() {
        super.onDestroy()
        infoPresenter.destroyDisposable()
    }
}
