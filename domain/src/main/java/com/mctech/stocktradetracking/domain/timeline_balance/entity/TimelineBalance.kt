package com.mctech.stocktradetracking.domain.timeline_balance.entity

import com.mctech.stocktradetracking.domain.extentions.formatBrazilianCurrency
import com.mctech.stocktradetracking.domain.extentions.round
import com.mctech.stocktradetracking.domain.extentions.toPercent
import java.io.Serializable
import java.util.*

data class TimelineBalance(
    val id : Long? = null,
    var periodTag : String,
    var parentPeriodId: Long? = null,
    val startDate: Date = Calendar.getInstance().time,
    var periodInvestment: Double = 0.0,
    var periodProfit: Double = 0.0,
    var parent: TimelineBalance? = null
) : Serializable {
    fun getFinalInvestmentBalance() : Double{
        return periodInvestment + (parent?.getFinalInvestmentBalance() ?: 0.0)
    }

    fun getFinalProfit() : Double{
        return (getFinalBalance() - getFinalInvestmentBalance())
    }

    fun getFinalBalance() : Double {
        return ((parent?.getFinalBalance() ?: 0.0 ) + periodInvestment + periodProfit).round(2)
    }

    fun getPeriodVariation() : Double {
        return try{
            (periodProfit / (parent?.getFinalBalance() ?: getFinalBalance()) * 100).round(2)
        }catch (ex : Exception){
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

    fun computeProfitByFinalBalance(finalBalance : Double) : Double{
        return (finalBalance - (parent?.getFinalBalance() ?: 0.0 ) - periodInvestment).round(2)
    }
}
