package com.rdd.finy.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.rdd.finy.App
import com.rdd.finy.R
import com.rdd.finy.data.Wallet
import com.rdd.finy.data.WalletRepository
import com.rdd.finy.presenters.SetupWalletPresenter
import com.rdd.finy.views.SetupWalletDialogView
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class SetupWalletDialog : MvpAppCompatDialogFragment(), SetupWalletDialogView {

    private val TAG = SetupWalletDialog::class.java.simpleName

    init{
        App.app()?.appComponent()?.inject(this@SetupWalletDialog)
    }

    @Inject
    lateinit var walletRepository: WalletRepository

    @InjectPresenter
    lateinit var setupWalletPresenter: SetupWalletPresenter

    @ProvidePresenter
    fun provideSetupWalletPresenter(): SetupWalletPresenter{
        return SetupWalletPresenter(walletRepository)
    }

    private lateinit var setupWalletTitleEdit: EditText
    private lateinit var setupWalletBalanceEdit: EditText
    private lateinit var setupWalletUpperDividerEdit: EditText
    private lateinit var setupWalletBottomDividerEdit: EditText
    private lateinit var deleteWalletBtn : Button

    private lateinit var localWallet: Wallet

    companion object {
        private var isCreatingWallet = true

        private const val ARG_WALLET_ID = "walletId"

        fun newInstance(walletId: Long): SetupWalletDialog {
            val args = Bundle()
            args.putLong(ARG_WALLET_ID, walletId)
            val fragment = SetupWalletDialog()
            fragment.arguments = args

            isCreatingWallet = false
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.dialog_setup_wallet, null)

        setupWalletTitleEdit = view.findViewById(R.id.etxt_setup_wallet_title)
        setupWalletBalanceEdit = view.findViewById(R.id.etxt_setup_wallet_balance)
        setupWalletUpperDividerEdit = view.findViewById(R.id.etxt_setup_wallet_upper_divider)
        setupWalletBottomDividerEdit = view.findViewById(R.id.etxt_setup_wallet_bottom_divider)
        deleteWalletBtn = view.findViewById(R.id.btn_setup_wallet_delete)

        deleteWalletBtn.setOnClickListener {
            walletRepository.deleteWallet(localWallet)
            dialog?.dismiss()
        }

        val dialog = AlertDialog.Builder(activity)
            .setView(view)
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                dismiss()
            }
            .create()

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

    override fun setupViewType() {
        if (arguments != null) {
            val walletId = arguments!!.getLong(ARG_WALLET_ID)
            setupWalletPresenter.setupEditViewWithWalletFromDB(walletId)
        } else {
            setupCreateView()
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

        val dialogTitle = if (isCreatingWallet) getString(R.string.wallet_setup_new_title_dialog) else localWallet.title
        dialog?.setTitle(dialogTitle)
    }

    private fun prepareResultWallet() {
        if (setupWalletBalanceEdit.text.toString() != "")
            localWallet.balance = setupWalletBalanceEdit.text.toString().toDouble()

        localWallet.title = setupWalletTitleEdit.text.toString()

        if (setupWalletUpperDividerEdit.text.toString() != "")
            localWallet.upperDivider = setupWalletUpperDividerEdit.text.toString().toDouble()

        if (setupWalletBottomDividerEdit.text.toString() != "")
            localWallet.bottomDivider = setupWalletBottomDividerEdit.text.toString().toDouble()
    }

    private fun sendResult() {
        if (isCreatingWallet) {
            walletRepository.insertWallet(localWallet)
        } else {
            walletRepository.updateWallet(localWallet)
        }
    }
}