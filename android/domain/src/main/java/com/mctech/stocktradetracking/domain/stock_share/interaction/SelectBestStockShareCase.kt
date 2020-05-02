package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

class SelectBestStockShareCase(
    private val groupStockShareListCase: GroupStockShareListCase
) {
    fun <R : Comparable<R>> execute(stockShareList: List<StockShare>, selector: (StockShare) -> R): StockShare? {
        return groupStockShareListCase.execute(stockShareList).maxBy {
            selector.invoke(it)
        }
    }
}