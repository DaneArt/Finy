package com.rdd.finy.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.arellomobile.mvp.MvpDialogFragment

class ChooseBackColorDialog : MvpDialogFragment(){

    companion object{

        private const val ARG_COLOR = "backgroundColorId"

        fun newInstance(backColorId: Int) : DialogFragment{

            val args = Bundle()
            args.putInt(ARG_COLOR, backColorId)

            val fragment = DialogFragment()
            fragment.arguments = args

            return fragment
        }
    }

}
