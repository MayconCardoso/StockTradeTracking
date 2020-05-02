package com.mctech.stocktradetracking.feature.stock_share

import android.os.Bundle
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

interface StockShareNavigator {
    fun fromStockListToBuyPosition()
    fun fromStockListToEditPosition(stockShare: StockShare)
    fun navigateBack()
}

fun stockShareFromBundle(bundle : Bundle) : StockShare{
    if (bundle.containsKey("stockShare")) {
        return bundle.getSerializable("stockShare") as StockShare
    }
    throw IllegalArgumentException("Required argument \"stockShare\" is missing and does not have an android:defaultValue")
}