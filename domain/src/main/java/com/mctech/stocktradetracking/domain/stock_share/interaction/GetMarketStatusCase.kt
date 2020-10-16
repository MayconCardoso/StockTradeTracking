package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.stock_share.entity.MarketStatus
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class GetMarketStatusCase(
  private val service: StockShareService,
  private val logger: Logger
) {
  suspend fun execute(): Result<MarketStatus> {
    return try {
      Result.Success(service.getMarketStatus())
    } catch (ex: Exception) {
      logger.e(e = ex)
      Result.Failure(ex)
    }
  }
}