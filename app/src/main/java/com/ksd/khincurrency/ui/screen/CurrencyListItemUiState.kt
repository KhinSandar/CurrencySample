package com.ksd.khincurrency.ui.screen

data class CurrencyListItemUiState(
    val baseName: String,
    var rateValue: Double,
    var calculateAmount: Double? = 0.0
)