package com.mctech.stocktradetracking.data.timeline_balance.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "timeline_balance",
    indices = [
        Index(
            value = ["periodTag"],
            name = "timeline_balance_period_tag"
        )
    ]
)
data class TimelineBalanceDatabaseEntity(
    @PrimaryKey
    val periodTag : String,
    val parentPeriodTag : String?,
    val startDate : Date,
    val periodInvestment : Double,
    val periodProfit : Double
)