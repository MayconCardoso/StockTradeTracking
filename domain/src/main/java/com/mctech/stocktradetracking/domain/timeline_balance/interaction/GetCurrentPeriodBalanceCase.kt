package com.mctech.stocktradetracking.domain.timeline_balance.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.timeline_balance.TimelineBalanceError
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.service.TimelineBalanceService

class GetCurrentPeriodBalanceCase(
	private val service : TimelineBalanceService,
	private val logger	: Logger
){
	suspend fun execute() : Result<List<TimelineBalance>>{
		return try{
			val periodList 		= service.getListOfPeriodsBalance()

			// Set the parent period to each item.
			periodList.forEach { period ->
				period.parent = periodList.findLast {
					it.periodTag == period.parentPeriodTag
				}
			}

			Result.Success(periodList)
		}
		catch (ex : Exception){
			logger.e(e = ex)
			if(ex is TimelineBalanceError){
				Result.Failure(ex)
			}else {
				Result.Failure(TimelineBalanceError.UnknownExceptionException)
			}
		}
	}
}
