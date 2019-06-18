package com.rdd.finy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.rdd.finy.R
import com.rdd.finy.presenters.WalletPresenter
import com.rdd.finy.views.WalletView

class WalletFragment : MvpAppCompatFragment(),WalletView {


    @InjectPresenter
    lateinit var walletPresenter: WalletPresenter

    private lateinit var nameTxt: TextView
    private lateinit var countTxt: TextView
    private lateinit var walletBackground: RelativeLayout
    companion object{

        const val WALLET_ID_ARG = "walletId"

        fun newInstance(id: Long) : WalletFragment{

            val args = Bundle()
            args.putLong(WALLET_ID_ARG, id)

            val fragment = WalletFragment()

            fragment.arguments = args

            return fragment

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

       val view = inflater.inflate(R.layout.item_wallet, container, false)

        nameTxt = view.findViewById(R.id.wallet_nameTxt)
        countTxt = view.findViewById(R.id.wallet_countTxt)
        walletBackground = view.findViewById(R.id.wallet_back)

        val walletId:Long = arguments!!.getLong(WALLET_ID_ARG)
        walletPresenter.loadWalletById(walletId)

        return view

    }

    override fun setupViews(name: String, count: Int, colorId: Int) {
        nameTxt.text = name
        countTxt.text = count.toString()
        walletBackground.setBackgroundColor(colorId)
    }

}