package com.mctech.stocktradetracking.data.stock_share.datasource

import com.mctech.stocktradetracking.data.stock_share.database.StockShareDao
import com.mctech.stocktradetracking.data.stock_share.mapper.toDatabaseEntity
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import java.util.*

class LocalStockShareDataSource(
	private val stockShareDao: StockShareDao
) : StockShareDataSource {

	override suspend fun getStockShareList(): List<StockShare> {
		return mutableListOf<StockShare>().apply {
			add(
				StockShare(
					1,
					"MGLU3",
					200,
					27.92 ,
					50.65,
					Calendar.getInstance().time,
					Calendar.getInstance().time,
					true
				)
			)

			add(
				StockShare(
					1,
					"CNTO3",
					200,
					16.60,
					30.62,
					Calendar.getInstance().time,
					Calendar.getInstance().time,
					true
				)
			)

			add(
				StockShare(
					1,
					"WEGE3",
					200,
					45.03,
					40.47,
					Calendar.getInstance().time,
					Calendar.getInstance().time,
					true
				)
			)
		}
	}

	override suspend fun buyStockShare(share: StockShare) {
		stockShareDao.save(share.toDatabaseEntity())
	}

	override suspend fun sellStockShare(share: StockShare) {
		stockShareDao.save(share.toDatabaseEntity())
	}

	override suspend fun editStockShareValue(shareCode: String, value: Double) {
		stockShareDao.editStockSharePrice(shareCode, value)
	}
}