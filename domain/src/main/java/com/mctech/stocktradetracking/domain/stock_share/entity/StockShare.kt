package com.mctech.stocktradetracking.domain.stock_share.entity

import java.util.*

data class StockShare(
    val id : Long,
    val code : String,
    val shareAmount : Int,
    val purchasePrice : Double,
    var salePrice : Double = purchasePrice,
    val purchaseDate : Date,
    var saleDate : Date? = null,
    var isPositionOpened : Boolean = true
)
