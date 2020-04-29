package com.mctech.stocktradetracking.data.stock_share.repository

import com.mctech.stocktradetracking.data.stock_share.datasource.StockShareDataSource
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class StockShareRepository(
	private val localDataSource: StockShareDataSource
) : StockShareService by localDataSource
