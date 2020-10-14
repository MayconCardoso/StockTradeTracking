package com.mctech.stocktradetracking.data.stock_share.datasource

import com.mctech.library.core.networking.secureRequest
import com.mctech.stocktradetracking.data.stock_share.api.StockSharePriceAPI

class RemoteStockShareDataSourceImpl(
  private val api: StockSharePriceAPI
) : RemoteStockShareDataSource {

  override suspend fun getCurrentStockSharePrice(stockCode: String) = secureRequest {
    api.getCurrentStockPrice(stockCode)
  }

}