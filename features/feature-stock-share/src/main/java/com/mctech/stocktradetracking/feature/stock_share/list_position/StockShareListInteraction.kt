package com.mctech.stocktradetracking.feature.stock_share.list_position

import com.mctech.architecture.mvvm.x.core.UserInteraction

sealed class StockShareListInteraction : UserInteraction {
    object LoadStockShare : StockShareListInteraction()
    object SyncStockPrice : StockShareListInteraction()

    object StartRealtimePosition : StockShareListInteraction()
    object StopRealtimePosition : StockShareListInteraction()
}