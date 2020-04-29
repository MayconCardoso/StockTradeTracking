package com.mctech.stocktradetracking.domain.timeline_balance.entity

import com.mctech.stocktradetracking.domain.extentions.formatBrazilianCurrency
import com.mctech.stocktradetracking.domain.extentions.round
import com.mctech.stocktradetracking.domain.extentions.toPercent
import java.util.*

data class TimelineBalance(
    val periodTag : String,
    val parentPeriodTag: String?,
    val startDate: Date = Calendar.getInstance().time,
    val periodInvestment: Double = 0.0,
    val periodProfit: Double = 0.0,
    var parent: TimelineBalance? = null
){
    fun getFinalInvestmentBalance() : Double{
        return periodInvestment + (parent?.getFinalInvestmentBalance() ?: 0.0)
    }

    fun getFinalProfit() : Double{
        return (getFinalBalance() - getFinalInvestmentBalance())
    }

    fun getFinalBalance() : Double {
        return (parent?.getFinalBalance() ?: 0.0 ) + periodInvestment + periodProfit
    }

    fun getPeriodVariation() : Double {
        return try{
            (periodProfit / (parent?.getFinalBalance() ?: getFinalBalance()) * 100).round(2)
        }catch (ex : ArithmeticException){
            0.0
        }
    }

    fun getFinalInvestmentBalanceDescription() : String{
        return getFinalInvestmentBalance().formatBrazilianCurrency()
    }

    fun getPeriodInvestmentDescription() : String{
        return periodInvestment.formatBrazilianCurrency()
    }

    fun getPeriodProfitDescription() : String{
        return periodProfit.formatBrazilianCurrency()
    }

    fun getFinalProfitDescription() : String{
        return getFinalProfit().formatBrazilianCurrency()
    }

    fun getFinalBalanceDescription() : String {
        return getFinalBalance().formatBrazilianCurrency()
    }

    fun getPeriodVariationDescription() : String {
        return getPeriodVariation().toPercent()
    }
}
