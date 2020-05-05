package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.SelectedStock
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.SelectStockStrategy

class SelectWorstStockShareCase(
    private val groupStockShareListCase: GroupStockShareListCase
) : SelectStockStrategy {
    override fun execute(stockShareList: List<StockShare>): SelectedStock? {
        return groupStockShareListCase.transform(stockShareList).minBy {
            it.getBalance()
        }?.let {
            SelectedStock(
                it.code,
                it.getBalanceDescription(),
                it.getVariationDescription(),
                it.getVariation()
            )
        }
    }
}