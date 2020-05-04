package com.mctech.stocktradetracking.di.data

import com.mctech.stocktradetracking.data.timeline_balance.datasource.LocalTimelineBalanceDataSource
import com.mctech.stocktradetracking.data.timeline_balance.datasource.TimelineBalanceDataSource
import com.mctech.stocktradetracking.data.timeline_balance.repository.TimelineBalanceRepository
import com.mctech.stocktradetracking.domain.timeline_balance.service.TimelineBalanceService
import org.koin.dsl.module

val timelineBalanceDataModule = module {
    single {
        LocalTimelineBalanceDataSource(
            dao = get()
        ) as TimelineBalanceDataSource
    }

    single {
        TimelineBalanceRepository(
            localDataSource = get()
        ) as TimelineBalanceService
    }
}
