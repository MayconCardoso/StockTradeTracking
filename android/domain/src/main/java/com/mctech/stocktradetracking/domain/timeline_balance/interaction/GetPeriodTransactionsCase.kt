package com.mctech.stocktradetracking.domain.timeline_balance.interaction

import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.service.TimelineBalanceService

class GetPeriodTransactionsCase(private val service : TimelineBalanceService){
	suspend fun execute(period: TimelineBalance) : Result<List<TimelineBalance>>{
		return try{
			Result.Success(service.getPeriodTransactions(period))
		}
		catch (ex : Exception){
			ex.printStackTrace()
			Result.Failure(ex)
		}
	}
}
