package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.ComputeBalanceStrategy

class GetFinalBalanceCase : ComputeBalanceStrategy() {
  override fun getConsideredBalance(item: StockShare) = item.getBalance()
}