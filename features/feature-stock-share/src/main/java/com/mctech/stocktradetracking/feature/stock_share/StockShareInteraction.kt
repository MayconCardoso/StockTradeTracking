package com.mctech.stocktradetracking.feature.stock_share

import com.mctech.architecture.mvvm.x.core.UserInteraction

sealed class StockShareInteraction : UserInteraction {
    sealed class List : StockShareInteraction(){
        object LoadStockShare : List()
    }
}