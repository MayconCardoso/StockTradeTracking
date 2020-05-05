package com.mctech.stocktradetracking.data.stock_share.api

import retrofit2.http.GET
import retrofit2.http.Query

interface StockSharePriceAPI {
    @GET("v1/stock")
    suspend fun getCurrentStockPrice(
        @Query("stockCode") stockCode: String
    ): StockPriceResponse
}

data class StockPriceResponse(
    val price: Double?,
    val open: Double?,
    val high: Double?,
    val low: Double?,
    val volume: Double?,
    val previousClose: Double?,
    val marketChange: Double
)