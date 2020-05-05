package com.mctech.stocktradetracking.data.timeline_balance.mapper

import com.mctech.stocktradetracking.data.timeline_balance.database.TimelineBalanceDatabaseEntity
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance

fun TimelineBalance.toDatabaseEntity() = TimelineBalanceDatabaseEntity(
    id = id,
    periodTag = periodTag,
    parentPeriodId = parentPeriodId,
    startDate = startDate,
    periodInvestment = periodInvestment,
    periodProfit = periodProfit
)

fun TimelineBalanceDatabaseEntity.toBusinessEntity() : TimelineBalance{
    return TimelineBalance(
        id = id,
        periodTag = periodTag,
        parentPeriodId = parentPeriodId,
        startDate = startDate,
        periodInvestment = periodInvestment,
        periodProfit = periodProfit
    )
}