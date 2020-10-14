package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class SplitStockShareCase(
  private val service: StockShareService,
  private val logger: Logger
) {
  suspend fun execute(share: StockShare, splitRatio: Int) {
    try {
      // Load all stocks of the code.
      val stockPositions = service.getAllByCode(share.code)

      // Compute new buy price
      stockPositions.forEach { item ->
        // Calculate new amount of shares after split
        val newAmountOfShares = item.shareAmount * splitRatio

        // Calculate new base price.
        item.purchasePrice = item.getInvestmentValue() / newAmountOfShares

        // Change new amount
        item.shareAmount = newAmountOfShares

        // Save it
        service.saveStockShare(item)
      }
    } catch (ex: Exception) {
      logger.e(e = ex)
    }
  }
}
