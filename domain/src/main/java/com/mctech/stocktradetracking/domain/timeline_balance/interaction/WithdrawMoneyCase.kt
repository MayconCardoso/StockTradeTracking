package com.mctech.stocktradetracking.domain.timeline_balance.interaction

import com.mctech.stocktradetracking.domain.timeline_balance.service.TimelineBalanceService

class WithdrawMoneyCase(private val service : TimelineBalanceService){
	suspend fun execute(amount: Double) {
		try{
			service.withdrawMoney(amount)
		}
		catch (ex : Exception){
			ex.printStackTrace()
			TODO("You must handle the error here.")
		}
	}
}
