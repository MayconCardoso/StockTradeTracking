package com.mctech.stocktradetracking.domain.stock_share.interaction.strategies

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import kotlinx.coroutines.flow.Flow

interface ObserveStockListStrategy {
  suspend fun execute(): Flow<List<StockShare>>
}
