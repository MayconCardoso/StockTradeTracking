package com.mctech.stocktradetracking.domain.timeline_balance.interaction

import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.service.TimelineBalanceService

class EditPeriodCase(private val service : TimelineBalanceService){
	suspend fun execute(period: TimelineBalance) {
		try{
			service.editPeriod(period)
		}
		catch (ex : Exception){
			ex.printStackTrace()
			TODO("You must handle the error here.")
		}
	}
}
