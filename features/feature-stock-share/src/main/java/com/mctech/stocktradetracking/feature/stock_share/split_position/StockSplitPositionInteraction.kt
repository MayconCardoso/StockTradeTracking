package com.mctech.stocktradetracking.feature.stock_share.split_position

import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

sealed class StockSplitPositionInteraction : UserInteraction {
  data class OpenStockShareDetails(
    val item: StockShare
  ) : StockSplitPositionInteraction()

  data class SplitStock(
    val splitRatio: Int
  ) : StockSplitPositionInteraction()
}