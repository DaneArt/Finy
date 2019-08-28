package com.rdd.finy.app.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.rdd.finy.App
import com.rdd.finy.R
import com.rdd.finy.app.models.Wallet
import com.rdd.finy.app.presenters.SetupWalletPresenter
import com.rdd.finy.app.views.SetupWalletDialogView
import com.rdd.finy.data.repositories.WalletRepositoryImpl
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
    lateinit var walletRepositoryImpl: WalletRepositoryImpl

    @InjectPresenter
    lateinit var setupWalletPresenter: SetupWalletPresenter

    @ProvidePresenter
    fun provideSetupWalletPresenter(): SetupWalletPresenter {
        return SetupWalletPresenter(walletRepositoryImpl)
    }

    @BindView(R.id.etxt_setup_wallet_title)
    lateinit var setupWalletTitleEdit: EditText
    @BindView(R.id.etxt_setup_wallet_balance)
    lateinit var setupWalletBalanceEdit: EditText
    @BindView(R.id.etxt_setup_wallet_upper_divider)
    lateinit var setupWalletUpperDividerEdit: EditText
    @BindView(R.id.etxt_setup_wallet_bottom_divider)
    lateinit var setupWalletBottomDividerEdit: EditText
    @BindView(R.id.btn_setup_wallet_delete)
    lateinit var deleteWalletBtn: Button
    @BindView(R.id.img_balance_alert)
    lateinit var alertBalanceImg: ImageView
    @BindView(R.id.img_upper_divider_alert)
    lateinit var alertUpperDividerImg: ImageView
    @BindView(R.id.img_bottom_divider_alert)
    lateinit var alertBottomDividerImg: ImageView


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
        isCreatingWallet = arguments == null
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.dialog_setup_wallet, null)

        ButterKnife.bind(this, view)

        val builder = AlertDialog.Builder(activity, R.style.AddWalletStyle)

        val dialogTitle = if (isCreatingWallet)
            getString(R.string.wallet_setup_new_title_dialog)
        else getString(R.string.wallet_edit_title_dialog)
        view.findViewById<TextView>(R.id.txt_setup_wallet_dialog_title).text = dialogTitle

        builder.setView(view)
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                dismiss()
            }

        val dialog = builder.create()

        dialog.setOnShowListener {
            val positiveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                buildResultWallet()
                sendResult()
            }
        }

        setupViewType()

        return dialog
    }

    @OnClick(R.id.btn_setup_wallet_delete)
    fun deleteWallet() {
        walletRepositoryImpl.delete(localWallet)
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

        setupWalletBalanceEdit.setText(setupWalletPresenter.buildBalanceString(localWallet.balance))

        if (localWallet.upperDivider != null) {
            setupWalletUpperDividerEdit.setText(setupWalletPresenter.buildBalanceString(localWallet.upperDivider!!))
        }

        if (localWallet.bottomDivider != null) {
            setupWalletBottomDividerEdit.setText(setupWalletPresenter.buildBalanceString(localWallet.bottomDivider!!))
        }

        deleteWalletBtn.visibility = View.VISIBLE
    }


    override fun buildResultWallet() {

        if (setupWalletTitleEdit.text.isNotBlank()) {
            localWallet.title = setupWalletTitleEdit.text.toString()
        } else {
            localWallet.title = context!!.getString(R.string.without_title)
        }

        if (setupWalletBalanceEdit.text.isNotBlank())
            localWallet.balance = setupWalletPresenter
                    .getSaveableMoney(setupWalletBalanceEdit.text.toString())
        else
            localWallet.balance = 0

        validateUpperDivider()
        validateBottomDivider()
    }

    override fun validateUpperDivider() {
        try {
            if (setupWalletUpperDividerEdit.text.isNotBlank())
                localWallet.upperDivider = setupWalletPresenter
                        .getSaveableMoney(setupWalletUpperDividerEdit.text.toString())
            alertUpperDividerImg.visibility = View.GONE
        } catch (t: Throwable) {
            alertUpperDividerImg.visibility = View.VISIBLE
        }
    }

    override fun validateBottomDivider() {
        try {
            if (setupWalletBottomDividerEdit.text.isNotBlank())
                localWallet.bottomDivider = setupWalletPresenter
                        .getSaveableMoney(setupWalletBottomDividerEdit.text.toString())
            alertBottomDividerImg.visibility = View.GONE
        } catch (t: Throwable) {
            alertBottomDividerImg.visibility = View.VISIBLE
        }
    }

    override fun sendResult() {
        if (alertBalanceImg.visibility == View.GONE &&
                alertBottomDividerImg.visibility == View.GONE &&
                alertUpperDividerImg.visibility == View.GONE
        ) {
            if (isCreatingWallet) {
                walletRepositoryImpl.insert(localWallet)
            } else {
                walletRepositoryImpl.update(localWallet)
            }

            dismiss()
        }
    }
}