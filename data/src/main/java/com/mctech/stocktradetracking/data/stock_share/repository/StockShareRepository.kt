package com.mctech.stocktradetracking.data.stock_share.repository

import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService
import com.mctech.stocktradetracking.data.stock_share.datasource.LocalStockShareDataSource

class StockShareRepository(
	private val localDataSource: LocalStockShareDataSource
) : StockShareService by localDataSource
