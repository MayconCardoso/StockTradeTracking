package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.stock_share.StockShareError
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class GetStockShareListCase(
	private val service : StockShareService,
	private val logger : Logger
){
	suspend fun execute() : Result<List<StockShare>>{
		return try{
			Result.Success(service.getStockShareList())
		}
		catch (ex : Exception){
			logger.e(e = ex)
			if(ex is StockShareError){
				Result.Failure(ex)
			}else {
				Result.Failure(StockShareError.UnknownExceptionException)
			}
		}
	}
}
