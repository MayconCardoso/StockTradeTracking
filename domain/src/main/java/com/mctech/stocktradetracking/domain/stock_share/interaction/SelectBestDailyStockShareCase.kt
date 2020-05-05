package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.SelectedStock
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.SelectStockStrategy

class SelectBestDailyStockShareCase(
    private val groupStockShareListCase: GroupStockShareListCase
) : SelectStockStrategy {
    override fun execute(stockShareList: List<StockShare>): SelectedStock? {
        return groupStockShareListCase.transform(stockShareList).maxBy {
            it.getDailyVariationBalance()
        }?.let {
            SelectedStock(
                it.code,
                it.getDailyVariationBalanceDescription(),
                it.getDailyVariationDescription(),
                it.getDailyVariationBalance()
            )
        }
    }
}