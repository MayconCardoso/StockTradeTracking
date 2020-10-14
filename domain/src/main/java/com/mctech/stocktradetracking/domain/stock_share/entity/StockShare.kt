package com.mctech.stocktradetracking.domain.stock_share.entity

import com.mctech.stocktradetracking.domain.extentions.formatBrazilianCurrency
import com.mctech.stocktradetracking.domain.extentions.round
import com.mctech.stocktradetracking.domain.extentions.toPercent
import java.io.Serializable
import java.util.Calendar
import java.util.Date

data class StockShare(
  val id: Long? = null,
  var code: String,
  var shareAmount: Long,
  var purchasePrice: Double,
  var salePrice: Double = purchasePrice,
  val purchaseDate: Date = Calendar.getInstance().time,
  var saleDate: Date? = null,
  var isPositionOpened: Boolean,

  var marketChange: Double? = null,
  var previousClose: Double? = null

) : Serializable {
  fun getBuyDescription(): String {
    return "BUY $shareAmount @ ${purchasePrice.formatBrazilianCurrency()}"
  }

  fun getSellDescription(): String {
    return "SELL $shareAmount @ ${salePrice.formatBrazilianCurrency()}"
  }

  fun getInvestmentValue(): Double {
    return shareAmount * purchasePrice
  }

  fun getInvestmentValueDescription(): String {
    return getInvestmentValue().formatBrazilianCurrency()
  }

  fun getFinalStockPrice(): Double {
    return shareAmount * salePrice
  }

  fun getFinalStockPriceDescription(): String {
    return getFinalStockPrice().formatBrazilianCurrency()
  }

  fun getBalance(): Double {
    return (getFinalStockPrice() - getInvestmentValue()).round(2)
  }

  fun getBalanceDescription(): String {
    return getBalance().formatBrazilianCurrency()
  }

  fun getVariation(): Double {
    if (getInvestmentValue() == 0.0) {
      return 0.0
    }
    return (getBalance() / getInvestmentValue() * 100).round(2)
  }

  fun getVariationDescription(): String {
    return getVariation().toPercent()
  }

  fun getPreviousCloseDescription(): String {
    return "Previous Close " + (previousClose ?: 0.0).formatBrazilianCurrency()
  }

  fun getCurrentPriceDescription(): String {
    return "Current Price " + salePrice.formatBrazilianCurrency()
  }

  fun getDailyVariation(): Double {
    if (previousClose == 0.0) {
      return 0.0
    }
    return previousClose?.let {
      return ((salePrice / it * 100) - 100).round(2)
    } ?: 0.0
  }

  fun getDailyVariationDescription(): String {
    return getDailyVariation().toPercent()
  }

  fun getDailyVariationBalance(): Double {
    if (previousClose == 0.0) {
      return 0.0
    }
    return previousClose?.let {
      ((salePrice - it) * shareAmount).round(2)
    } ?: 0.0
  }

  fun getDailyVariationBalanceDescription(): String {
    return getDailyVariationBalance().formatBrazilianCurrency()
  }
}