package com.fazal.assesment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
/**
 * Created by Fazal on 01/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */
@Parcelize
data class ProductItem(
    @SerializedName("product_name") var productName: String? = null,
    @SerializedName("product_type") var productType: String? = null,
    @SerializedName("price") var price: Double? = null,
    @SerializedName("tax") var tax: String? = null,
    @SerializedName("image") var image: String? = null
): Parcelable