package com.mctech.stocktradetracking.data.stock_share.datasource

import com.mctech.stocktradetracking.data.stock_share.database.StockShareDao
import com.mctech.stocktradetracking.data.stock_share.mapper.toDatabaseEntity
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import kotlinx.coroutines.flow.Flow

class LocalStockShareDataSourceImpl(
	private val stockShareDao: StockShareDao
) : LocalStockShareDataSource {

	override suspend fun observeStockShareList(): Flow<List<StockShare>> {
		return stockShareDao.observeAllOpenedPosition()
	}

	override suspend fun getStockShareList(): List<StockShare> {
		return stockShareDao.loadAllOpenedPosition()
	}

	override suspend fun getDistinctStockCode(): List<String> {
		return stockShareDao.loadDistinctStockCodes()
	}

	override suspend fun saveStockShare(share: StockShare) {
		val databaseEntity  = share.toDatabaseEntity()

		// Try to load the same position to update the right price.s
		stockShareDao.loadStockSharePosition(share.code)?.let {
			databaseEntity.salePrice = it.salePrice
		}

		stockShareDao.save(databaseEntity)
	}

	override suspend fun sellStockShare(share: StockShare) {
		stockShareDao.save(share.toDatabaseEntity())
	}

	override suspend fun deleteStockShare(share: StockShare) {
		stockShareDao.delete(share.toDatabaseEntity())
	}

	override suspend fun editStockShareValue(shareCode: String, value: Double) {
		stockShareDao.editStockSharePrice(shareCode, value)
	}
}