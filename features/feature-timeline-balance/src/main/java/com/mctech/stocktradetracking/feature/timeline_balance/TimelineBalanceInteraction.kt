package com.mctech.stocktradetracking.feature.timeline_balance

import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance

sealed class TimelineBalanceInteraction : UserInteraction {

    object LoadTimelineComponent : TimelineBalanceInteraction()

    data class CreatePeriod(
        val period: String,
        val monthInvestment : Double,
        val monthProfit : Double
    ) : TimelineBalanceInteraction()

    data class EditPeriod(
        val period: String,
        val monthInvestment : Double,
        val monthProfit : Double,
        val finalBalance : Double
    ) : TimelineBalanceInteraction()

    data class OpenPeriodDetails(
        val period: TimelineBalance
    ) : TimelineBalanceInteraction()
}