package com.fazal.assesment.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.fazal.assesment.databinding.DialogTwoButtonBinding

/**
 * Created by Fazal on 02/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */

fun showTwoButtonDialog(
    context: Context,
    title: String? = null,
    msg: String? = null,
    txtPrimary: String? = null,
    txtSecondary: String?=null,
    onDismissListener: DialogInterface.OnClickListener? = null
) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val binding: DialogTwoButtonBinding =
        DialogTwoButtonBinding.inflate(LayoutInflater.from(context))
    dialog.setContentView(binding.root)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window!!.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
    dialog.setCanceledOnTouchOutside(false)
    dialog.show()

    title?.let { binding.tvTitle.text = it }
    msg?.let { binding.tvMessage.text = it }
    txtPrimary?.let { binding.tvBtnPrimary.text = it }
    txtSecondary?.let { binding.tvBtnSecondary.text = it }

    binding.tvBtnPrimary.setOnClickListener {
        onDismissListener?.onClick(dialog, DialogInterface.BUTTON_POSITIVE)
        dialog.dismiss()
    }

    binding.tvBtnSecondary.setOnClickListener {
        onDismissListener?.onClick(dialog, DialogInterface.BUTTON_NEGATIVE)
        dialog.dismiss()
    }

}