package com.ksd.khincurrency.ui.screen

data class CurrencyListItemUiState(
    val baseName: String,
    val rateValue: Double,
    val calculateAmount: Double? = 0.0
)