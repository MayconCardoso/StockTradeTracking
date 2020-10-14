package com.mctech.stocktradetracking.domain.stock_share_filter.service

import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter
import kotlinx.coroutines.flow.Flow

interface StockShareFilterService {
  fun observeStockShareFilter(): Flow<StockFilter?>
  suspend fun saveFilter(stockFilter: StockFilter)
}
