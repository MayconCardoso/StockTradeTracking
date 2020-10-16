package com.mctech.stocktradetracking.data.stock_share.datasource

import com.mctech.stocktradetracking.domain.stock_share.entity.MarketStatus
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import kotlinx.coroutines.flow.Flow

interface LocalStockShareDataSource {
  fun observeStockShareList(): Flow<List<StockShare>>
  fun observeStockClosedList(): Flow<List<StockShare>>

  suspend fun getMarketStatus(): MarketStatus
  suspend fun getAllByCode(code: String): List<StockShare>

  suspend fun getDistinctStockCode(): List<String>

  suspend fun saveStockShare(share: StockShare)
  suspend fun sellStockShare(share: StockShare)
  suspend fun deleteStockShare(share: StockShare)
  suspend fun closeStockShare(share: StockShare)
  suspend fun editStockShareValue(
    shareCode: String,
    currentPrice: Double,
    marketChange: Double? = null,
    previousClose: Double? = null
  )

}
