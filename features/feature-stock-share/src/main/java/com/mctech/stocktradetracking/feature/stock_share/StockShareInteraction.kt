package com.mctech.stocktradetracking.feature.stock_share

import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

sealed class StockShareInteraction : UserInteraction {
    sealed class List : StockShareInteraction() {
        object LoadStockShare : List()
        data class OpenStockShareDetails(val item: StockShare) : List()
    }
}