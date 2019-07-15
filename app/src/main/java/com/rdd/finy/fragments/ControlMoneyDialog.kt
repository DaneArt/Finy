package com.rdd.finy.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdd.finy.R
import com.rdd.finy.adapters.ControlWalletsAdapter
import com.rdd.finy.adapters.MoneyWalletsAdapter
import com.rdd.finy.data.Wallet
import com.rdd.finy.presenters.ControlMoneyPresenter
import com.rdd.finy.views.ControlMoneyView
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter

class ControlMoneyDialog : MvpAppCompatDialogFragment(), ControlMoneyView {


    @InjectPresenter
    lateinit var controlMoneyPresenter: ControlMoneyPresenter

    private lateinit var controlBalanceEdit: EditText
    private lateinit var controlMoneyEdit: EditText
    private lateinit var controlMoneyTxt: TextView
    private lateinit var controlMoneyWalletsTxt: TextView
    private lateinit var controlMoneyWalletsRv: RecyclerView

    private lateinit var walletsAdapter: MoneyWalletsAdapter
    private lateinit var controlWalletsAdapter: ControlWalletsAdapter

    companion object {

        private const val ARG_CONTROL_FLAG = "controlMoneyFlag"

        fun newInstance(isAddingMoney: Boolean): ControlMoneyDialog {

            val args = Bundle()
            args.putBoolean(ARG_CONTROL_FLAG, isAddingMoney)

            val fragment = ControlMoneyDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_control, null)

        controlBalanceEdit = view.findViewById(R.id.edit_control_balance)
        controlMoneyEdit = view.findViewById(R.id.etxt_control_for_history)
        controlMoneyTxt = view.findViewById(R.id.txt_control_for_history)
        controlMoneyWalletsRv = view.findViewById(R.id.rv_control_wallets_list)

        walletsAdapter = MoneyWalletsAdapter(context!!)
        controlWalletsAdapter = ControlWalletsAdapter(context!!)

        controlMoneyWalletsRv.layoutManager = LinearLayoutManager(context)

        controlMoneyWalletsRv.adapter = controlWalletsAdapter
        controlWalletsAdapter.setInnerAdapter(walletsAdapter)


        if (arguments!!.getBoolean(ARG_CONTROL_FLAG)) {
            setupInsertView()
        } else {
            setupRemoveView()
        }

        val title = if (arguments!!.getBoolean(ARG_CONTROL_FLAG))
            getString(R.string.add_money_title)
        else getString(R.string.remove_money_title)

        controlMoneyPresenter.loadWalletsFromDB()

        return AlertDialog.Builder(activity)
                .setView(view)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok) { _, _ ->

                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                    dismiss()
                }
                .create()
    }

    override fun setupSourceList(wallets: List<Wallet>) {
        walletsAdapter.setupWalletsList(wallets)
    }

    override fun setupInsertView() {
        controlMoneyTxt.text = getString(R.string.control_source_of_money)
    }

    override fun setupRemoveView() {
        controlMoneyTxt.text = getString(R.string.control_destination_for_history)
    }


}