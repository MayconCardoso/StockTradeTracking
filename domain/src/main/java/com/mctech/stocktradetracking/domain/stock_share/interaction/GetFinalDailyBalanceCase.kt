package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.ComputeBalanceStrategy

class GetFinalDailyBalanceCase : ComputeBalanceStrategy() {
  override fun getConsideredBalance(item: StockShare) = item.getDailyVariationBalance()
}