package com.mctech.stocktradetracking.data.stock_share.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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

  @Transaction
  @Query("SELECT * FROM stock_share WHERE isPositionOpened = 0 ORDER BY code")
  fun observeStockClosedList(): Flow<List<StockShare>>


  // ============================================================================
  // Single shot
  // ============================================================================
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

  @Query("UPDATE stock_share SET salePrice = :currentPrice WHERE code = :code AND isPositionOpened = 1 ")
  suspend fun editStockSharePriceManually(code: String, currentPrice: Double)

  @Query("UPDATE stock_share SET isPositionOpened = 0, salePrice = :salePrice WHERE id = :id")
  suspend fun closeStockShare(id: Long, salePrice: Double)

  @Query("UPDATE stock_share SET salePrice = :currentPrice, previousClose = :previousClose, marketChange = :marketChange WHERE code = :code AND isPositionOpened = 1")
  suspend fun editStockSharePriceAutomatically(
    code: String,
    currentPrice: Double,
    marketChange: Double,
    previousClose: Double
  )
}