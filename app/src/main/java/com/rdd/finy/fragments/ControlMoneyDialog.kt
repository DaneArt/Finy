package com.rdd.finy.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdd.finy.R
import com.rdd.finy.adapters.MoneyWalletsAdapter
import com.rdd.finy.data.Wallet
import com.rdd.finy.helpers.AddCalculator
import com.rdd.finy.helpers.CalculatorBehaviour
import com.rdd.finy.helpers.RemoveCalculator
import com.rdd.finy.presenters.ControlMoneyPresenter
import com.rdd.finy.views.ControlMoneyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import moxy.MvpAppCompatDialogFragment
import moxy.presenter.InjectPresenter


class ControlMoneyDialog : MvpAppCompatDialogFragment(), ControlMoneyView {

    private val TAG = ControlMoneyDialog::class.java.simpleName

    @InjectPresenter
    lateinit var controlMoneyPresenter: ControlMoneyPresenter

    private lateinit var controlBalanceEdit: EditText
    private lateinit var controlHistoryEdit: EditText
    private lateinit var controlHistoryTxt: TextView

    private lateinit var controlWalletsCountTxt: TextView
    private lateinit var controlWalletsCountImView: ImageView
    private lateinit var controlStateViewBtn: LinearLayout
    private lateinit var walletsListRv: RecyclerView

    private lateinit var walletsAdapter: MoneyWalletsAdapter

    private var rvVisibility = false

    private lateinit var openAnim: Animation
    private lateinit var closeAnim: Animation

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
        controlHistoryEdit = view.findViewById(R.id.etxt_control_for_history)
        controlHistoryTxt = view.findViewById(R.id.txt_control_for_history)
        controlWalletsCountTxt = view.findViewById(R.id.txt_control_wallets_count)
        controlWalletsCountImView = view.findViewById(R.id.im_control_wallets_count)
        controlStateViewBtn = view.findViewById(R.id.control_wallets_count_background)
        walletsListRv = view.findViewById(R.id.rv_money_wallets)

        openAnim = AnimationUtils.loadAnimation(context, R.anim.open_wallets_list)
        closeAnim = AnimationUtils.loadAnimation(context, R.anim.close_wallets_list)

        walletsAdapter = MoneyWalletsAdapter(context!!)

        walletsListRv.adapter = walletsAdapter
        walletsListRv.layoutManager = LinearLayoutManager(context)

        controlStateViewBtn.setOnClickListener {
            rvVisibility = !rvVisibility
            setupRvVisibility()
        }

        controlBalanceEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotBlank()) walletsAdapter.setUserBalance(s.toString().toDouble())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        if (arguments!!.getBoolean(ARG_CONTROL_FLAG)) {
            setupInsertView()
        } else {
            setupRemoveView()
        }

        val title = if (arguments!!.getBoolean(ARG_CONTROL_FLAG))
            getString(R.string.add_money_title)
        else getString(R.string.remove_money_title)

        controlMoneyPresenter.loadWalletsFromDB()

        controlWalletsCountTxt.text = getString(R.string.control_wallets_count,
                0)

        walletsAdapter.getActiveWalletsSize()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { count ->
                    controlWalletsCountTxt.text = getString(R.string.control_wallets_count,
                            count)
                }

        val dialog = AlertDialog.Builder(activity)
                .setView(view)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                    dismiss()
                }
                .create()

        dialog.setOnShowListener {
            val positiveButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                val calculator: CalculatorBehaviour

                if (arguments!!.getBoolean(ARG_CONTROL_FLAG)) {
                    calculator = AddCalculator(walletsAdapter.getActiveWallets())
                    Log.e(TAG, "Add money calc")
                } else {
                    calculator = RemoveCalculator(walletsAdapter.getActiveWallets())
                    Log.e(TAG, "Remove money calc")
                }

                if (controlBalanceEdit.text.isNotBlank()) {
                    calculator.userBalance = controlBalanceEdit.text.toString().toDouble()
                    calculator.calculate()
                }
                dismiss()
            }
        }

        return dialog
    }

    override fun setupRvVisibility() {
        if (rvVisibility) {
            walletsListRv.visibility = View.VISIBLE
            openWalletsList()
        } else {
            walletsListRv.visibility = View.GONE
            closeWalletsList()
        }
    }

    override fun openWalletsList() {
        controlWalletsCountImView.setImageResource(android.R.drawable.arrow_up_float)
        controlWalletsCountImView.startAnimation(openAnim)
    }

    override fun closeWalletsList() {
        controlWalletsCountImView.setImageResource(android.R.drawable.arrow_down_float)
        controlWalletsCountImView.startAnimation(closeAnim)
    }

    override fun setupSourceList(wallets: List<Wallet>) {
        walletsAdapter.setupWalletsList(wallets)
    }

    override fun setupInsertView() {
        controlHistoryTxt.text = getString(R.string.control_source_of_money)
    }

    override fun setupRemoveView() {
        controlHistoryTxt.text = getString(R.string.control_destination_for_history)
    }


}