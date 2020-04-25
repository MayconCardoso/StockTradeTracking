package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class BuyStockShareCase(private val service : StockShareService){
	suspend fun execute(share: StockShare) {
		try{
			service.buyStockShare(share)
		}
		catch (ex : Exception){
			ex.printStackTrace()
			TODO("You must handle the error here.")
		}
	}
}
