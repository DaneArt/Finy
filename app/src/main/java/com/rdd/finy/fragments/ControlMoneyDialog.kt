package com.rdd.finy.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.rdd.finy.R
import com.rdd.finy.presenters.ControlMoneyPresenter
import com.rdd.finy.views.ControlMoneyView
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter

class ControlMoneyDialog : MvpAppCompatDialogFragment(), ControlMoneyView {

    @InjectPresenter
    lateinit var controlMoneyPresenter: ControlMoneyPresenter

    private lateinit var controlBalanceEdit : EditText
    private lateinit var controlSourceDestinationEdit: EditText
    private lateinit var controlWalletsSourceDestinationBtn : Button
    private lateinit var controlSourceDestinationTxt: TextView
    private lateinit var controlWalletsSourceDestinationTxt : TextView

    companion object{

        private const val ARG_CONTROL_FLAG = "controlMoneyFlag"

        fun newInstance(isAddingMoney : Boolean): ControlMoneyDialog{

            val args = Bundle()
            args.putBoolean(ARG_CONTROL_FLAG, isAddingMoney)

            val fragment = ControlMoneyDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_control,null)

        controlBalanceEdit = view.findViewById(R.id.etxt_control_balance)
        controlSourceDestinationEdit = view.findViewById(R.id.etxt_control_for_history)
        controlWalletsSourceDestinationBtn = view.findViewById(R.id.btn_control_wallets)
        controlSourceDestinationTxt = view.findViewById(R.id.txt_control_for_history)
        controlWalletsSourceDestinationTxt = view.findViewById(R.id.txt_control_wallets)

        if(arguments!!.getBoolean(ARG_CONTROL_FLAG)){
            setupInsertView()
        }else{
            setupRemoveView()
        }

        return AlertDialog.Builder(activity)
            .setView(view)
            .setPositiveButton(android.R.string.ok) { _, _ ->

            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                dismiss()
            }
            .create()
    }

    override fun setupInsertView() {
        controlSourceDestinationTxt.text = getString(R.string.control_source_of_money)
        controlWalletsSourceDestinationTxt.text = getString(R.string.control_destination_wallets)
    }

    override fun setupRemoveView() {
        controlSourceDestinationTxt.text = getString(R.string.control_destination_for_history)
        controlWalletsSourceDestinationTxt.text = getString(R.string.control_source_of_money)
    }


}