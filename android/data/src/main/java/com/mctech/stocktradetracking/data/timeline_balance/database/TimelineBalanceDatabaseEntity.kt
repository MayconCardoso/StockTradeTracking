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
            name = "timeline_balance_period_tag",
            unique = true
        )
    ]
)
data class TimelineBalanceDatabaseEntity(
    @PrimaryKey
    val id : Long? = null,
    val periodTag : String,
    val parentPeriodId : Long? = null,
    val startDate : Date = Calendar.getInstance().time,
    val periodInvestment : Double,
    val periodProfit : Double
)