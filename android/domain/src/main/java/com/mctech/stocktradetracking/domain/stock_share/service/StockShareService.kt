package com.mctech.stocktradetracking.domain.stock_share.service

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
interface StockShareService{
	suspend fun getStockShareList(): List<StockShare>
	suspend fun saveStockShare(share: StockShare)
	suspend fun sellStockShare(share: StockShare)
	suspend fun deleteStockShare(share: StockShare)
	suspend fun editStockShareValue(shareCode: String, value: Double)
	suspend fun syncStockSharePrice()
}
