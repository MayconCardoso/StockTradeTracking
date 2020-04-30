package com.mctech.stocktradetracking.data.stock_share.datasource

import com.mctech.stocktradetracking.data.stock_share.api.StockPriceResponse

interface RemoteStockShareDataSource {
    suspend fun getCurrentStockSharePrice(stockCode : String) : StockPriceResponse
}
