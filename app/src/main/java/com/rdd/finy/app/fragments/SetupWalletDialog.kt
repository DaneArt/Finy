package com.rdd.finy.app.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.rdd.finy.App
import com.rdd.finy.R
import com.rdd.finy.app.models.Wallet
import com.rdd.finy.app.presenters.SetupWalletPresenter
import com.rdd.finy.app.views.SetupWalletDialogView
import com.rdd.finy.data.repositories.WalletRepository
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class SetupWalletDialog : MvpAppCompatDialogFragment(), SetupWalletDialogView {

    private val TAG = SetupWalletDialog::class.java.simpleName

    init {
        App.app()?.appComponent()?.inject(this@SetupWalletDialog)
    }

    @Inject
    lateinit var walletRepository: WalletRepository

    @InjectPresenter
    lateinit var setupWalletPresenter: SetupWalletPresenter

    @ProvidePresenter
    fun provideSetupWalletPresenter(): SetupWalletPresenter {
        return SetupWalletPresenter(walletRepository)
    }

    @BindView(R.id.etxt_setup_wallet_title)
    private lateinit var setupWalletTitleEdit: EditText
    @BindView(R.id.etxt_setup_wallet_balance)
    private lateinit var setupWalletBalanceEdit: EditText
    @BindView(R.id.etxt_setup_wallet_upper_divider)
    private lateinit var setupWalletUpperDividerEdit: EditText
    @BindView(R.id.etxt_setup_wallet_bottom_divider)
    private lateinit var setupWalletBottomDividerEdit: EditText
    @BindView(R.id.btn_setup_wallet_delete)
    private lateinit var deleteWalletBtn: Button

    private var localWallet: Wallet = Wallet()
    private var isCreatingWallet = true

    companion object {

        private const val ARG_WALLET_ID = "walletId"

        fun newInstance(walletId: Long): SetupWalletDialog {
            val args = Bundle()
            args.putLong(ARG_WALLET_ID, walletId)
            val fragment = SetupWalletDialog()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        isCreatingWallet = arguments==null

        val view = LayoutInflater.from(activity)
            .inflate(R.layout.dialog_setup_wallet, null)
        ButterKnife.bind(this,view)

        var dialogTitle = if (isCreatingWallet) getString(R.string.wallet_setup_new_title_dialog) else localWallet.title

        val dialog = AlertDialog.Builder(activity)
            .setView(view)
            .setTitle(dialogTitle)
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                dismiss()
            }
            .create()

        dialog.window.setBackgroundDrawableResource(R.color.colorPrimaryLight)

        dialog.setOnShowListener {
            val positiveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                prepareResultWallet()
                if (localWallet.couldBeSaved) {
                    sendResult()
                    dialog.dismiss()
                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.save_wallet_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        setupViewType()

        return dialog
    }

    @OnClick(R.id.btn_setup_wallet_delete)
    fun deleteWallet(){
        walletRepository.deleteWallet(localWallet)
        dialog?.dismiss()
    }

    override fun setupViewType() {
        if (arguments != null) {
            val walletId = arguments!!.getLong(ARG_WALLET_ID)
            setupWalletPresenter.setupEditViewWithWalletFromDB(walletId)
            Log.e(TAG, "Edit wallet setup")
        } else {
            setupCreateView()
            Log.e(TAG, "Create wallet setup")
        }

    }

    override fun setupCreateView() {
        localWallet = Wallet()
    }

    override fun setupEditView(wallet: Wallet) {
        localWallet = wallet

        setupWalletTitleEdit.setText(localWallet.title)
        setupWalletBalanceEdit.setText(localWallet.balance.toString())
        if (localWallet.hasUpperDivider)
            setupWalletUpperDividerEdit.setText(localWallet.upperDivider.toString())
        if (localWallet.hasBottomDivider)
            setupWalletBottomDividerEdit.setText(localWallet.bottomDivider.toString())

        deleteWalletBtn.visibility = View.VISIBLE
    }

    private fun prepareResultWallet() {
        if (setupWalletBalanceEdit.text.toString() != "")
            localWallet.balance = setupWalletBalanceEdit.text.toString().toDouble()
        else
            localWallet.balance = 0.0

        localWallet.title = setupWalletTitleEdit.text.toString()

        if (setupWalletUpperDividerEdit.text.toString() != "")
            localWallet.upperDivider = setupWalletUpperDividerEdit.text.toString().toDouble()
        else
            localWallet.upperDivider = -1.0

        if (setupWalletBottomDividerEdit.text.toString() != "" )
            localWallet.bottomDivider = setupWalletBottomDividerEdit.text.toString().toDouble()
        else
            localWallet.bottomDivider = 0.0
    }

    private fun sendResult() {
        Log.e(TAG, "Result sent, $isCreatingWallet")
        if (isCreatingWallet) {
            walletRepository.insertWallet(localWallet)
        } else {
            walletRepository.updateWallet(localWallet)
        }
    }
}