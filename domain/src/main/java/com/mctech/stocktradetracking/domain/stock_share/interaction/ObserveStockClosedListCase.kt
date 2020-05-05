package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.ObserveStockListStrategy
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class ObserveStockClosedListCase(
    private val service: StockShareService
) : ObserveStockListStrategy {
    override suspend fun execute() = service.observeStockClosedList()
}
