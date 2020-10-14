package com.mctech.stocktradetracking.feature.timeline_balance.add_period

import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.interaction.CreatePeriodCase

class TimelineBalanceAddViewModel constructor(
  private val createPeriodCase: CreatePeriodCase
) : BaseViewModel() {

  override suspend fun handleUserInteraction(interaction: UserInteraction) {
    when (interaction) {
      is TimelineBalanceAddInteraction.CreatePeriod -> {
        createPeriodInteraction(
          interaction.period,
          interaction.monthInvestment,
          interaction.monthProfit
        )
      }
    }
  }

  private suspend fun createPeriodInteraction(
    periodTag: String,
    monthInvestment: Double,
    monthProfit: Double
  ) {
    val period = TimelineBalance(
      periodTag = periodTag,
      periodInvestment = monthInvestment,
      periodProfit = monthProfit
    )

    // Save new position
    createPeriodCase.execute(period)

    // Return to list
    sendCommand(TimelineBalanceAddCommand.NavigationBack)
  }
}
