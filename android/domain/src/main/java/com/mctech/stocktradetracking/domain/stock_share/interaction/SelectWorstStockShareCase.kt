package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

class SelectWorstStockShareCase(
    private val groupStockShareListCase: GroupStockShareListCase
) {
    fun <R : Comparable<R>> execute(stockShareList: List<StockShare>, selector: (StockShare) -> R): StockShare? {
        return groupStockShareListCase.execute(stockShareList).minBy {
            selector.invoke(it)
        }
    }
}