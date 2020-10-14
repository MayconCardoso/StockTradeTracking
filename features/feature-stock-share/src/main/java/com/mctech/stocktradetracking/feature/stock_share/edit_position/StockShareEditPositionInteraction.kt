package com.mctech.stocktradetracking.feature.stock_share.edit_position

import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

sealed class StockShareEditPositionInteraction : UserInteraction {
  data class OpenStockShareDetails(
    val item: StockShare
  ) : StockShareEditPositionInteraction()

  data class UpdateStockPrice(
    val code: String,
    val amount: Long,
    val purchasePrice: Double,
    val currentPrice: Double
  ) : StockShareEditPositionInteraction()

  object DeleteStockShare : StockShareEditPositionInteraction()
  object SplitStockShare : StockShareEditPositionInteraction()
  data class CloseStockPosition(val price: Double?) : StockShareEditPositionInteraction()
}