package com.mctech.stocktradetracking.domain.stock_share.entity

import com.mctech.stocktradetracking.domain.extentions.formatBrazilianCurrency
import com.mctech.stocktradetracking.domain.extentions.round
import com.mctech.stocktradetracking.domain.extentions.toPercent

data class StockShareFinalBalance(
  val balance: Double,
  val investment: Double,
  val variation: Double
) {
  fun getBalanceDescription(): String {
    return balance.formatBrazilianCurrency()
  }

  fun getInvestmentDescription(): String {
    return investment.formatBrazilianCurrency()
  }

  fun getFinalValueDescription(): String {
    return (investment + balance).formatBrazilianCurrency()
  }

  fun getVariationDescription(): String {
    return variation.round(2).toPercent()
  }
}