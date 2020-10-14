package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.extentions.round
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShareFinalBalance
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.ComputeBalanceStrategy

class GetFinalBalanceCase : ComputeBalanceStrategy {
  override fun execute(stockShareList: List<StockShare>): StockShareFinalBalance {
    var balance = 0.0
    var investment = 0.0

    for (stock in stockShareList) {
      investment += stock.getFinalStockPrice()
      balance += stock.getBalance()
    }

    val variation = if (investment == 0.0) {
      0.0
    } else
      (((investment + balance) / investment * 100) - 100).round(2)

    return StockShareFinalBalance(
      balance,
      investment,
      variation
    )
  }
}