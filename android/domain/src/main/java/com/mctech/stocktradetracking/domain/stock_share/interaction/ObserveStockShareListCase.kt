package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class ObserveStockShareListCase(
    private val service: StockShareService
) {
    suspend fun execute() = service.observeStockShareList()
}
