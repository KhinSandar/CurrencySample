package com.ksd.khincurrency.repository.network.model

import com.google.gson.annotations.SerializedName

data class CurrencyInfo(
    @SerializedName("disclaimer")
    val disclaimer: String,
    @SerializedName("license")
    val license: String,
    @SerializedName("timestamp")
    val timestamp: Int,
    @SerializedName("base")
    val base: String,
    @SerializedName("rates")
    val rates: CurrencyRate
)
