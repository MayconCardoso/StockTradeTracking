package com.mctech.stocktradetracking.data.timeline_balance.repository

import com.mctech.stocktradetracking.data.timeline_balance.datasource.TimelineBalanceDataSource
import com.mctech.stocktradetracking.domain.timeline_balance.service.TimelineBalanceService

class TimelineBalanceRepository(
	private val localDataSource: TimelineBalanceDataSource
) : TimelineBalanceService by localDataSource
