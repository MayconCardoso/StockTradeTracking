package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShareFinalBalance
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.ComputeBalanceStrategy

class GetFinalDailyBalanceCase : ComputeBalanceStrategy {

    override fun execute(stockShareList: List<StockShare>): StockShareFinalBalance {
        var balance = 0.0
        var investment = 0.0
        var variation = 0.0

        for (stock in stockShareList) {
            investment += stock.getInvestmentValue()
            balance += stock.getDailyVariationBalance()
            variation = stock.getDailyVariation()
        }

        return StockShareFinalBalance(
            balance,
            investment,
            variation
        )
    }
}