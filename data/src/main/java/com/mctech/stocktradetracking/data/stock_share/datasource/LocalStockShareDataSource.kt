package com.mctech.stocktradetracking.data.stock_share.datasource

import com.mctech.stocktradetracking.data.stock_share.database.StockShareDao
import com.mctech.stocktradetracking.data.stock_share.mapper.toDatabaseEntity
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

class LocalStockShareDataSource(
	private val stockShareDao: StockShareDao
) : StockShareDataSource{

	override suspend fun getStockShareList(): List<StockShare>{
		return stockShareDao.loadAllOpenedPosition()
	}

	override suspend fun buyStockShare(share: StockShare){
		stockShareDao.save(share.toDatabaseEntity())
	}

	override suspend fun sellStockShare(share: StockShare){
		stockShareDao.save(share.toDatabaseEntity())
	}

	override suspend fun editStockShareValue(shareCode: String, value: Double){
		stockShareDao.editStockSharePrice(shareCode, value)
	}
}