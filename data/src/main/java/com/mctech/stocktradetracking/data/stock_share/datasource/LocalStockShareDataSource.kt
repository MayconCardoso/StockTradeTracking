package com.mctech.stocktradetracking.data.stock_share.datasource

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

class LocalStockShareDataSource : StockShareDataSource{

	override suspend fun getStockShareList(): StockShare{
		TODO()
	}

	override suspend fun buyStockShare(share: StockShare){
		TODO()
	}

	override suspend fun sellStockShare(share: StockShare, value: Double){
		TODO()
	}

	override suspend fun editStockShareValue(shareCode: String, value: Double){
		TODO()
	}

}
