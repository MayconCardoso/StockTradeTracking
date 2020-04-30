package com.mctech.stocktradetracking.feature.timeline_balance.edit_period

import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance

sealed class TimelineBalanceEditInteraction : UserInteraction {
    data class EditPeriod(
        val period: String,
        val monthInvestment : Double,
        val monthProfit : Double,
        val finalBalance : Double
    ) : TimelineBalanceEditInteraction()

    data class OpenPeriodDetails(
        val period: TimelineBalance
    ) : TimelineBalanceEditInteraction()
}