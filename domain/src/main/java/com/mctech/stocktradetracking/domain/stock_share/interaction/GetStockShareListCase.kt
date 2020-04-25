package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class GetStockShareListCase(private val service : StockShareService){
	suspend fun execute() : Result<StockShare>{
		return try{
			Result.Success(service.getStockShareList())
		}
		catch (ex : Exception){
			ex.printStackTrace()
			Result.Failure(ex)
		}
	}
}
