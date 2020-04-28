package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShareFinalBalance

class GetFinalBalanceCase {
    fun execute(stockShareList: List<StockShare>): StockShareFinalBalance {
        var balance = 0.0
        var investment = 0.0

        for (stock in stockShareList) {
            investment += stock.getInvestmentValue()
            balance += stock.getBalance()
        }

        return StockShareFinalBalance(
            balance,
            investment
        )
    }
}