package com.mctech.stocktradetracking.domain.stock_share.entity

data class SelectedStock(
    val code : String,
    val balanceDescription : String,
    val variationDescription : String,
    val variation : Double
)