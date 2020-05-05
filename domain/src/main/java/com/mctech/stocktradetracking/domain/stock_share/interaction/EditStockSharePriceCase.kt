package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class EditStockSharePriceCase(
	private val service : StockShareService,
	private val logger : Logger
){
	suspend fun execute(shareCode: String, value: Double) {
		try{
			service.editStockShareValue(shareCode, value)
		}
		catch (ex : Exception){
			logger.e(e = ex)
		}
	}
}
