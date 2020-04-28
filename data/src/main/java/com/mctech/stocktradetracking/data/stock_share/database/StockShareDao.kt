package com.mctech.stocktradetracking.data.stock_share.database

import androidx.room.*
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

@Dao
interface StockShareDao {
    @Transaction
    @Query("SELECT * FROM stock_share WHERE isPositionOpened = 1 ORDER BY code")
    suspend fun loadAllOpenedPosition(): List<StockShare>

    @Transaction
    @Query("SELECT * FROM stock_share WHERE isPositionOpened = 1 AND code = :code LIMIT 1")
    suspend fun loadStockSharePosition(code: String): StockShare?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(stockShare: StockShareDatabaseEntity)

    @Transaction
    @Delete
    suspend fun delete(stockShare: StockShareDatabaseEntity)

    @Query("UPDATE stock_share SET salePrice = :currentPrice WHERE code = :code")
    suspend fun editStockSharePrice(code: String, currentPrice: Double)
}