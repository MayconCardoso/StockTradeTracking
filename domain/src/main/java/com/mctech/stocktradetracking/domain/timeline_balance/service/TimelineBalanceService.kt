package com.mctech.stocktradetracking.domain.timeline_balance.service

import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
interface TimelineBalanceService{
	suspend fun getLastPeriod(): TimelineBalance?
	suspend fun getListOfPeriodsBalance(): List<TimelineBalance>

	suspend fun createPeriod(period: TimelineBalance)
	suspend fun editPeriod(period: TimelineBalance)
}
