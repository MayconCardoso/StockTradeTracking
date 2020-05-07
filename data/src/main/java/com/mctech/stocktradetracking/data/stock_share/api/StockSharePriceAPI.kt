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
    val price: Double? = null,
    val open: Double? = null,
    val high: Double? = null,
    val low: Double? = null,
    val volume: Double? = null,
    val previousClose: Double? = null,
    val marketChange: Double? = null
)