package com.mctech.stocktradetracking.domain.timeline_balance.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.service.TimelineBalanceService

class CreatePeriodCase(
	private val service : TimelineBalanceService,
	private val logger 	: Logger
){
	suspend fun execute(period: TimelineBalance, parent: TimelineBalance?) {
		try{
			period.parentPeriodId = parent?.id
			service.createPeriod(period)
		}
		catch (ex : Exception){
			logger.e(e = ex)
		}
	}
}
