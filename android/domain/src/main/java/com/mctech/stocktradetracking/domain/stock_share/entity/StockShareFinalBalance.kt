package com.mctech.stocktradetracking.domain.stock_share.entity

import com.mctech.stocktradetracking.domain.extentions.formatBrazilianCurrency
import com.mctech.stocktradetracking.domain.extentions.round
import com.mctech.stocktradetracking.domain.extentions.toPercent

data class StockShareFinalBalance(
    val balance: Double,
    val investment : Double
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

    fun getVariant() : Double {
        if(investment == 0.0){
            return 0.0
        }
        return (((investment + balance) / investment * 100) - 100).round(2)
    }

    fun getVariationDescription(): String {
        return getVariant().round(2).toPercent()
    }
}