package com.mctech.stocktradetracking.feature.stock_share.add_position

import com.mctech.architecture.mvvm.x.core.UserInteraction

sealed class StockShareBuyInteraction : UserInteraction {
  data class AddPosition(
    val code: String,
    val amount: Long,
    val price: Double
  ) : StockShareBuyInteraction()
}