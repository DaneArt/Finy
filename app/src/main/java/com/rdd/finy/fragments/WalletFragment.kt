package com.rdd.finy.fragments

import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rdd.finy.R
import com.rdd.finy.presenters.WalletPresenter
import com.rdd.finy.views.WalletView

class WalletFragment : MvpAppCompatFragment(), WalletView {

    private  val TAG = WalletFragment::class.java.simpleName

    @InjectPresenter
    lateinit var walletPresenter: WalletPresenter

    @ProvidePresenter
    fun provideWalletPresenter(): WalletPresenter{
        return WalletPresenter(this)
    }

    private lateinit var walletNameTxtView: EditText
    private lateinit var walletBudjetTxtView: EditText
    private lateinit var walletBackground: CardView
    private lateinit var walletEditBtn: ImageButton
    private lateinit var walletDeleteBtn: ImageButton
    private lateinit var walletConfirmChangesBtn: ImageButton
    private lateinit var walletDeclineChangesBtn: ImageButton

    companion object {

        private const val WALLET_ID_ARG = "walletId"
        private const val DIALOG_BACK_COLOR: String = "backColorDialog"
        private const val REQUEST_BACK_COLOR: Int = 0

        fun newInstance(id: Long): WalletFragment {

            val args = Bundle()
            args.putLong(WALLET_ID_ARG, id)

            val fragment = WalletFragment()

            fragment.arguments = args

            return fragment

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.item_wallet, container, false)

        walletNameTxtView = view.findViewById(R.id.wallet_nameTxt)
        walletBudjetTxtView = view.findViewById(R.id.wallet_countTxt)
        walletBackground = view.findViewById(R.id.wallet_back)
        walletEditBtn = view.findViewById(R.id.wallet_edit)
        walletDeleteBtn = view.findViewById(R.id.wallet_delete)
        walletConfirmChangesBtn = view.findViewById(R.id.wallet_confirmChanges)
        walletDeclineChangesBtn = view.findViewById(R.id.wallet_declineChanges)

        walletEditBtn.setOnClickListener {
            startEditingWallet()
        }

        walletDeleteBtn.setOnClickListener {
            walletPresenter.deleteWallet()
        }

        walletConfirmChangesBtn.setOnClickListener {
            walletPresenter.updateWalletState(wName = walletNameTxtView.text.toString(),wBudjet = walletBudjetTxtView.text.toString().toInt() ,wBackColorId = walletBackground.cardBackgroundColor.defaultColor)
        }

        walletDeclineChangesBtn.setOnClickListener {
            endEditingWallet()
        }

        /*walletBackground.setOnClickListener {
            val manager = fragmentManager
            val colorDialog = ChooseBackColorDialog.newInstance(walletBackground.cardBackgroundColor.defaultColor)
            colorDialog.setTargetFragment(this@WalletFragment, REQUEST_BACK_COLOR)
            colorDialog.show(manager, DIALOG_BACK_COLOR)

        }*/

        val walletId: Long = arguments!!.getLong(WALLET_ID_ARG)
        walletPresenter.loadWalletById(walletId)

        return view

    }

    override fun onPause() {
        super.onPause()
        val walletId: Long = arguments!!.getLong(WALLET_ID_ARG)
        walletPresenter.loadWalletById(walletId)
    }

    override fun startEditingWallet() {
        walletEditBtn.visibility = View.INVISIBLE
        walletDeleteBtn.visibility = View.VISIBLE
        walletConfirmChangesBtn.visibility = View.VISIBLE
        walletDeclineChangesBtn.visibility = View.VISIBLE
        walletNameTxtView.isEnabled = true
        walletBudjetTxtView.isEnabled = true
        walletBackground.isClickable = true
        walletBackground.isFocusable = true
    }

    override fun endEditingWallet() {
        walletEditBtn.visibility = View.VISIBLE
        walletDeleteBtn.visibility = View.INVISIBLE
        walletConfirmChangesBtn.visibility = View.INVISIBLE
        walletDeclineChangesBtn.visibility = View.INVISIBLE
        walletNameTxtView.isEnabled = false
        walletBudjetTxtView.isEnabled = false
        walletBackground.isClickable = false
        walletBackground.isFocusable = false
    }

    override fun updateViewState(name: String, budjet: Int, colorId: Int) {
        walletNameTxtView.setText(name)
        walletBudjetTxtView.setText(budjet.toString())
        walletBackground.setCardBackgroundColor(colorId)
    }

}