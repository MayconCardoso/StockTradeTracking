package com.mctech.stocktradetracking.domain.timeline_balance.interaction

import com.mctech.stocktradetracking.domain.timeline_balance.service.TimelineBalanceService

class DepositMoneyCase(private val service : TimelineBalanceService){
	suspend fun execute(amount: Double) {
		try{
			service.depositMoney(amount)
		}
		catch (ex : Exception){
			ex.printStackTrace()
			TODO("You must handle the error here.")
		}
	}
}
