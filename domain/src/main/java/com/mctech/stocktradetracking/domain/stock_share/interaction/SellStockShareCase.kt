package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class SellStockShareCase(private val service : StockShareService){
	suspend fun execute(share: StockShare, value: Double) {
		try{
			service.sellStockShare(share, value)
		}
		catch (ex : Exception){
			ex.printStackTrace()
			TODO("You must handle the error here.")
		}
	}
}
