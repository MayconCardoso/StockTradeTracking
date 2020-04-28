package com.mctech.stocktradetracking.feature.stock_share

import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

sealed class StockShareInteraction : UserInteraction {
    sealed class List : StockShareInteraction() {
        object LoadStockShare : List()
        data class OpenStockShareDetails(val item: StockShare) : List()
    }

    data class AddPosition(
        val code : String,
        val amount : Int,
        val price : Double
    ) : StockShareInteraction()

    data class UpdateStockPrice(
        val code : String,
        val amount: Int,
        val purchasePrice : Double,
        val currentPrice : Double
    ) : StockShareInteraction()
}