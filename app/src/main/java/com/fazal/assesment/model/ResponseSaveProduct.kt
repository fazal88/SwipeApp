package com.fazal.assesment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Fazal on 01/07/23.
 * Copyright (c) 2023 Mohd Fazal Shaikh. All rights reserved.
 */
@Parcelize
data class ResponseSaveProduct(

    @SerializedName("message") var message: String? = null,
    @SerializedName("product_details") var productDetails: ProductItem? = ProductItem(),
    @SerializedName("product_id") var productId: Int? = null,
    @SerializedName("success") var success: Boolean? = null

) : Parcelable

