package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

class GetWorstStockShareCase(
    private val groupStockShareListCase: GroupStockShareListCase
) {
    fun execute(stockShareList: List<StockShare>): StockShare {
        return groupStockShareListCase.execute(stockShareList).minBy {
            it.getBalance()
        }!!.apply {
            System.out.println(this)
        }
    }
}