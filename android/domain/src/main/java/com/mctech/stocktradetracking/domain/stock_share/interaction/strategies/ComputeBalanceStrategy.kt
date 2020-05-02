package com.mctech.stocktradetracking.domain.stock_share.interaction.strategies

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShareFinalBalance

interface ComputeBalanceStrategy {
    fun execute(stockShareList: List<StockShare>): StockShareFinalBalance
}