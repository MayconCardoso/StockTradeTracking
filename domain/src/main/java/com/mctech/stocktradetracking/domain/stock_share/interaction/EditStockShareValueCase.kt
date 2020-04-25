package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class EditStockShareValueCase(private val service : StockShareService){
	suspend fun execute(shareCode: String, value: Double) {
		try{
			service.editStockShareValue(shareCode, value)
		}
		catch (ex : Exception){
			ex.printStackTrace()
			TODO("You must handle the error here.")
		}
	}
}
