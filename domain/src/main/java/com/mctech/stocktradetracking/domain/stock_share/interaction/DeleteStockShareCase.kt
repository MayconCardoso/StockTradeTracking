package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class DeleteStockShareCase(
  private val service: StockShareService,
  private val logger: Logger
) {
  suspend fun execute(share: StockShare) {
    try {
      service.deleteStockShare(share)
    } catch (ex: Exception) {
      logger.e(e = ex)
    }
  }
}
