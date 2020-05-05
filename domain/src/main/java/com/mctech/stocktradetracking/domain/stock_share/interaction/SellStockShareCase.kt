package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService
import java.util.*

class SellStockShareCase(
	private val service : StockShareService,
	private val logger : Logger
){
	suspend fun execute(share: StockShare, value: Double, saleDate: Date) {
		try{
			share.saleDate = saleDate
			share.salePrice = value

			service.sellStockShare(share)
		}
		catch (ex : Exception){
			logger.e(e = ex)
		}
	}
}
