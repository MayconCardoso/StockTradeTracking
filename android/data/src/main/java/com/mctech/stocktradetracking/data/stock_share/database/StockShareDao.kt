package com.mctech.stocktradetracking.data.stock_share.database

import androidx.room.*
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import kotlinx.coroutines.flow.Flow

@Dao
interface StockShareDao {
    // ============================================================================
    // Realtime
    // ============================================================================
    @Transaction
    @Query("SELECT * FROM stock_share WHERE isPositionOpened = 1 ORDER BY code")
    fun observeAllOpenedPosition(): Flow<List<StockShare>>


    // ============================================================================
    // Single shot
    // ============================================================================
    @Transaction
    @Query("SELECT * FROM stock_share WHERE isPositionOpened = 1 ORDER BY code")
    suspend fun loadAllOpenedPosition(): List<StockShare>

    @Transaction
    @Query("SELECT DISTINCT code FROM stock_share WHERE isPositionOpened = 1")
    suspend fun loadDistinctStockCodes(): List<String>

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