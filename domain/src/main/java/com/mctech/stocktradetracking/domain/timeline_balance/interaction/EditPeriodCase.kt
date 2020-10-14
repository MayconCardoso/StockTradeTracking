package com.mctech.stocktradetracking.domain.timeline_balance.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.service.TimelineBalanceService

class EditPeriodCase(
  private val service: TimelineBalanceService,
  private val logger: Logger
) {
  suspend fun execute(period: TimelineBalance, newValue: TimelineBalance, finalBalance: Double) {
    try {
      // Flow control
      val changingFinalBalance = period.getFinalBalance() != finalBalance

      // Update period.
      period.periodTag = newValue.periodTag
      if (period.periodInvestment != newValue.periodInvestment) {
        period.periodInvestment = newValue.periodInvestment
      }
      if (changingFinalBalance) {
        period.periodProfit = period.computeProfitByFinalBalance(finalBalance)
      } else {
        period.periodProfit = newValue.periodProfit
      }
      service.editPeriod(period)
    } catch (ex: Exception) {
      logger.e(e = ex)
    }
  }
}
