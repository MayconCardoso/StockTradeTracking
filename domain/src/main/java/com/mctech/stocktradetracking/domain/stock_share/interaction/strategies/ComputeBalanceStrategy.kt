package com.mctech.stocktradetracking.domain.stock_share.interaction.strategies

import com.mctech.stocktradetracking.domain.extentions.round
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShareFinalBalance

/**
 * This class will compute the balance of a list of stocks into a [StockShareFinalBalance] instance.
 */
abstract class ComputeBalanceStrategy {
  fun execute(stockShareList: List<StockShare>): StockShareFinalBalance {
    var balance = 0.0
    var investment = 0.0

    for (stock in stockShareList) {
      investment += stock.getFinalStockPrice()
      balance += getConsideredBalance(stock)
    }

    val variation = if (investment == 0.0) {
      0.0
    } else {
      (((investment + balance) / investment * 100) - 100).round(2)
    }

    return StockShareFinalBalance(
      balance,
      investment,
      variation
    )
  }

  abstract fun getConsideredBalance(item: StockShare): Double
}