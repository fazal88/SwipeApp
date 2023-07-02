package com.fazal.assesment.utils

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.fazal.assesment.R
import java.text.DecimalFormat

/**
 * Created by Fazal on 02/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */

@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(item: String?) {
    try {
        item?.let {
            if(it.isNotEmpty()){
                Glide.with(this.context)
                    .load(item)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(this)
            }else{
                this.setImageResource(R.drawable.ic_placeholder)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
@BindingAdapter("setImageUri")
fun ImageView.setImageUri(item: Uri?) {
    try {
        item?.let {
            if(it.toString().isNotEmpty()){
                Glide.with(this.context)
                    .load(item)
                    .centerCrop()
                    .placeholder(R.drawable.ic_placeholder)
                    .into(this)
            }else{
                this.setImageResource(R.drawable.ic_placeholder)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


@BindingAdapter("setPrice")
fun TextView.setPrice(price: Double?) {
    try {
        price?.let {
            val df = DecimalFormat("#.##")
            val roundOff = df.format(it)
            text = "₹$roundOff"
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


@BindingAdapter("setVisibleGone")
fun View.setVisibleGone(isVisible: Boolean?) {
    isVisible?.let {
        visibility = if (it) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
@BindingAdapter("setVisible")
fun View.setVisible(isVisible: Boolean?) {
    isVisible?.let {
        visibility = if (it) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
}