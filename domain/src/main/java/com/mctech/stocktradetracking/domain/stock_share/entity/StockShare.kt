package com.mctech.stocktradetracking.domain.stock_share.entity

import com.mctech.stocktradetracking.domain.extentions.formatBrazilianCurrency
import com.mctech.stocktradetracking.domain.extentions.round
import com.mctech.stocktradetracking.domain.extentions.toPercent
import java.util.*

data class StockShare(
    val id: Long? = null,
    var code: String,
    var shareAmount: Long,
    var purchasePrice: Double,
    var salePrice: Double = purchasePrice,
    val purchaseDate: Date = Calendar.getInstance().time,
    var saleDate: Date? = null,
    var isPositionOpened: Boolean = true
) {
    fun getBuyDescription(): String {
        return "BUY $shareAmount @ ${purchasePrice.formatBrazilianCurrency()}"
    }

    fun getSellDescription(): String {
        return "SELL $shareAmount @ ${salePrice.formatBrazilianCurrency()}"
    }

    fun getInvestmentValue(): Double {
        return shareAmount * purchasePrice
    }

    fun getFinalStockPrice(): Double {
        return shareAmount * salePrice
    }

    fun getInvestmentValueDescription(): String {
        return getInvestmentValue().formatBrazilianCurrency()
    }

    fun getFinalStockPriceDescription(): String {
        return getFinalStockPrice().formatBrazilianCurrency()
    }

    fun getBalance() : Double{
        return getFinalStockPrice() - getInvestmentValue()
    }

    fun getBalanceDescription() : String{
        return getBalance().formatBrazilianCurrency()
    }

    fun getVariation(): Double {
        if(getInvestmentValue() == 0.0){
            return 0.0
        }
        return (getBalance() / getInvestmentValue() * 100).round(2)
    }

    fun getVariationDescription(): String {
        return getVariation().toPercent()
    }
}