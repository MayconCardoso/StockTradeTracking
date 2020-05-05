package com.mctech.stocktradetracking.data.stock_share_filter.repository

import com.mctech.stocktradetracking.data.stock_share_filter.datasource.LocalStockShareFilterDataSource
import com.mctech.stocktradetracking.domain.stock_share_filter.service.StockShareFilterService

class StockShareFilterRepository(
    private val dataSource: LocalStockShareFilterDataSource
) : StockShareFilterService by dataSource