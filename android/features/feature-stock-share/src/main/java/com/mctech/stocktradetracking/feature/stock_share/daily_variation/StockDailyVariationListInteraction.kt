package com.mctech.stocktradetracking.feature.stock_share.daily_variation

import com.mctech.architecture.mvvm.x.core.UserInteraction

sealed class StockDailyVariationListInteraction : UserInteraction {
    object StartRealtimePosition : StockDailyVariationListInteraction()
    object StopRealtimePosition  : StockDailyVariationListInteraction()
}