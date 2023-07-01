package com.fazal.assesment.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.fazal.assesment.R

/**
 * Created by Fazal on 02/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */

@BindingAdapter("setProductImage")
fun ImageView.setProductImage(item: String?) {
    try {
        item?.let {
            Glide.with(this.context)
                .load(item)
                .placeholder(R.drawable.ic_placeholder)
                .into(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


@BindingAdapter("setPrice")
fun TextView.setPrice(price: String?) {
    try {
        text = "₹${price ?: "NA"}"
    } catch (e: Exception) {
        e.printStackTrace()
    }
}