package com.mctech.stocktradetracking.domain.timeline_balance.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.service.TimelineBalanceService

class EditPeriodCase(
	private val service : TimelineBalanceService,
	private val logger  : Logger
){
	suspend fun execute(period: TimelineBalance, newValue : TimelineBalance, finalBalance : Double) {
		try{
			// Flow control
			var changingInitialInvestment = false
			var changingFinalBalance = false
			val isPeriodInvestmentDifferent = period.periodInvestment != newValue.periodInvestment

			if(isPeriodInvestmentDifferent){
				changingInitialInvestment = true
			}
			if(period.getFinalBalance() != finalBalance){
				changingFinalBalance = true
			}

			// Update period.
			period.periodTag = newValue.periodTag
			if(changingInitialInvestment){
				period.periodInvestment = newValue.periodInvestment
			}
			if(changingFinalBalance){
				period.periodProfit = period.computeProfitByFinalBalance(finalBalance)
			}
			else{
				period.periodProfit = newValue.periodProfit
			}
			service.editPeriod(period)
		}
		catch (ex : Exception){
			logger.e(e = ex)
		}
	}
}
