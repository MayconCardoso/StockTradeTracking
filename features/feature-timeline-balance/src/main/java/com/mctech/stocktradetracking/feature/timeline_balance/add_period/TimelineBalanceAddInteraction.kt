package com.mctech.stocktradetracking.feature.timeline_balance.add_period

import com.mctech.architecture.mvvm.x.core.UserInteraction

sealed class TimelineBalanceAddInteraction : UserInteraction {

  data class CreatePeriod(
    val period: String,
    val monthInvestment: Double,
    val monthProfit: Double
  ) : TimelineBalanceAddInteraction()

}