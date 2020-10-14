package com.mctech.stocksharetracking.feature.stock_share_filter

import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter

sealed class StockShareFilterInteraction : UserInteraction {
  data class ApplyFilter(
    val stockFilter: StockFilter
  ) : StockShareFilterInteraction()
}