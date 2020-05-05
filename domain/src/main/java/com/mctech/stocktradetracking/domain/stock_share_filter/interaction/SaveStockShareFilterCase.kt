package com.mctech.stocktradetracking.domain.stock_share_filter.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter
import com.mctech.stocktradetracking.domain.stock_share_filter.service.StockShareFilterService

class SaveStockShareFilterCase(
    private val service: StockShareFilterService,
    private val logger: Logger
) {
    suspend fun execute(filter: StockFilter) {
        try {
            service.saveFilter(filter)
        } catch (ex: Exception) {
            logger.e(e = ex)
        }
    }
}
