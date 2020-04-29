package com.mctech.stocktradetracking.domain.timeline_balance.entity

import java.util.*

data class TimelineBalance(
    val periodTag : String,
    val parentPeriodTag: String?,
    val startDate: Date,
    val periodInvestment: Double,
    val periodProfit: Double,
    val parent: TimelineBalance?
)
