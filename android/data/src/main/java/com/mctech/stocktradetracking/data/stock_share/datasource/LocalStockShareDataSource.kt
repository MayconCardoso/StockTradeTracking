package com.mctech.stocktradetracking.data.stock_share.datasource

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import kotlinx.coroutines.flow.Flow

interface LocalStockShareDataSource {
    suspend fun observeStockShareList(): Flow<List<StockShare>>
    suspend fun getStockShareList(): List<StockShare>
    suspend fun saveStockShare(share: StockShare)
    suspend fun sellStockShare(share: StockShare)
    suspend fun deleteStockShare(share: StockShare)
    suspend fun editStockShareValue(
        shareCode: String,
        currentPrice: Double,
        marketChange: Double? = null,
        previousClose : Double? = null
    )

    suspend fun getDistinctStockCode(): List<String>
}
