package com.mctech.stocktradetracking.data.timeline_balance.database

import androidx.room.*

@Dao
interface TimelineBalanceDao {
    @Transaction
    @Query("SELECT * FROM timeline_balance ORDER BY startDate DESC")
    suspend fun loadListOfPeriodsBalance(): List<TimelineBalanceDatabaseEntity>

    @Transaction
    @Query("SELECT * FROM timeline_balance ORDER BY startDate DESC LIMIT 1")
    suspend fun loadLastPeriod(): TimelineBalanceDatabaseEntity?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(period: TimelineBalanceDatabaseEntity)

    @Transaction
    @Delete
    suspend fun delete(period: TimelineBalanceDatabaseEntity)
}