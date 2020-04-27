package com.mctech.stocktradetracking.domain.stock_share.entity

import com.mctech.stocktradetracking.domain.extentions.formatBrazilianCurrency
import com.mctech.stocktradetracking.domain.extentions.round
import java.util.*

data class StockShare(
    val id: Long? = null,
    val code: String,
    val shareAmount: Int,
    val purchasePrice: Double,
    var salePrice: Double = purchasePrice,
    val purchaseDate: Date,
    var saleDate: Date? = null,
    var isPositionOpened: Boolean = true
) {
    fun getBuyDescription(): String {
        return "BUY $shareAmount @ ${purchasePrice.formatBrazilianCurrency()}"
    }

    fun getSellDescription(): String {
        return "SELL $shareAmount @ ${salePrice.formatBrazilianCurrency()}"
    }

    fun getOriginalStockPrice(): Double {
        return shareAmount * purchasePrice
    }

    fun getFinalStockPrice(): Double {
        return shareAmount * salePrice
    }

    fun getOriginalStockPriceDescription(): String {
        return getOriginalStockPrice().formatBrazilianCurrency()
    }

    fun getFinalStockPriceDescription(): String {
        return getFinalStockPrice().formatBrazilianCurrency()
    }

    fun getBalance() : Double{
        return getFinalStockPrice() - getOriginalStockPrice()
    }

    fun getBalanceDescription() : String{
        return getBalance().formatBrazilianCurrency()
    }

    fun getVariation(): Double {
        if(getOriginalStockPrice() == 0.0){
            return 0.0
        }
        return (getBalance() / getOriginalStockPrice() * 100).round(2)
    }

    fun getVariationDescription(): String {
        return "${getVariation()}%"
    }
}