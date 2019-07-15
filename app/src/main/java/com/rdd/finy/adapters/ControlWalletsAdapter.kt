package com.rdd.finy.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdd.finy.R

class ControlWalletsAdapter(private val context: Context) :
        RecyclerView.Adapter<ControlWalletsAdapter.ControlMoneyViewHolder>() {

    private val TAG = ControlWalletsAdapter::class.java.simpleName

    private var walletsListAdapter: MoneyWalletsAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControlMoneyViewHolder {
        val inflater = LayoutInflater.from(context)
        return ControlMoneyViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ControlMoneyViewHolder, position: Int) {
        holder.bind()
    }

    fun setInnerAdapter(walletsAdapter: MoneyWalletsAdapter) {
        walletsListAdapter = walletsAdapter
        notifyItemChanged(0)
    }

    inner class ControlMoneyViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_money_wallets_list, parent, false)) {

        private val controlStateViewText: TextView = itemView.findViewById(R.id.txt_control_wallets_count)
        private val controlStateImageView: ImageView = itemView.findViewById(R.id.im_control_wallets_count)
        private val controlStateViewBtn: LinearLayout = itemView.findViewById(R.id.control_wallets_count_background)
        private val walletsListRv: RecyclerView = itemView.findViewById(R.id.rv_money_wallets)

        private var rvVisibility = false

        private lateinit var openAnim : Animation
        private lateinit var closeAnim : Animation

        fun bind() {

            openAnim = AnimationUtils.loadAnimation(context, R.anim.open_wallets_list)
            closeAnim = AnimationUtils.loadAnimation(context, R.anim.close_wallets_list)

            controlStateViewText.text = context.getString(
                    R.string.control_wallets_count,
                    walletsListAdapter?.getActiveWallets()!!.size)

            controlStateViewBtn.setOnClickListener {
                rvVisibility = !rvVisibility
                setupRvVisibility()
                Log.e(TAG, "Recycle visibility ${walletsListRv.visibility}")
            }
            if (walletsListAdapter!=null) {
                walletsListRv.layoutManager = LinearLayoutManager(context)
                walletsListRv.adapter = walletsListAdapter
            }

        }

        private fun setupRvVisibility(){
            if(rvVisibility){
                walletsListRv.visibility = View.VISIBLE
                openWalletsList()
            }else{
                walletsListRv.visibility = View.GONE
                closeWalletsList()
            }
        }

        private fun openWalletsList(){
            controlStateImageView.setImageResource(android.R.drawable.arrow_up_float)
            controlStateImageView.startAnimation(openAnim)
        }

        private fun closeWalletsList(){
            controlStateImageView.setImageResource(android.R.drawable.arrow_down_float)
            controlStateImageView.startAnimation(closeAnim)
        }


    }
}