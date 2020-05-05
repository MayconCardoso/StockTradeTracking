package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class SyncStockSharePriceCase(
	private val service : StockShareService,
	private val logger : Logger
){
	suspend fun execute() {
		try{
			service.syncStockSharePrice()
		}
		catch (ex : Exception){
			logger.e(e = ex)
		}
	}
}
