package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

class GetBestStockShareCase(
    private val groupStockShareListCase: GroupStockShareListCase
) {
    fun execute(stockShareList: List<StockShare>): StockShare {
        return groupStockShareListCase.execute(stockShareList).maxBy {
            it.getBalance()
        }!!.apply {
            System.out.println(this)
        }
    }
}