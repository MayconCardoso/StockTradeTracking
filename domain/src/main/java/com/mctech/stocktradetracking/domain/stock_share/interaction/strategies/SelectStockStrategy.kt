package com.mctech.stocktradetracking.domain.stock_share.interaction.strategies

import com.mctech.stocktradetracking.domain.stock_share.entity.SelectedStock
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter

interface SelectStockStrategy {
  fun execute(stockShareList: List<StockShare>, filter: StockFilter): SelectedStock?
}