package com.mctech.stocktradetracking.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mctech.stocktradetracking.data.stock_share.database.StockShareDao
import com.mctech.stocktradetracking.data.stock_share.database.StockShareDatabaseEntity
import com.mctech.stocktradetracking.data.timeline_balance.database.TimelineBalanceDao
import com.mctech.stocktradetracking.data.timeline_balance.database.TimelineBalanceDatabaseEntity

@Database(
  version = 1,
  entities = [
    StockShareDatabaseEntity::class,
    TimelineBalanceDatabaseEntity::class
  ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
  abstract fun stockShareDao(): StockShareDao
  abstract fun timelineBalanceDao(): TimelineBalanceDao
}