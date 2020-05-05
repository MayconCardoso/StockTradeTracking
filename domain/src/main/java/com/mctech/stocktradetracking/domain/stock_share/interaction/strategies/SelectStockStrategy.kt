package com.mctech.stocktradetracking.domain.stock_share.interaction.strategies

import com.mctech.stocktradetracking.domain.stock_share.entity.SelectedStock
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

interface SelectStockStrategy {
    fun execute(stockShareList: List<StockShare>): SelectedStock?
}