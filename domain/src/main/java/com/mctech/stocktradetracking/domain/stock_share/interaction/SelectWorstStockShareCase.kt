package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.SelectedStock
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.SelectStockStrategy
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.RankingQualifier
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter

class SelectWorstStockShareCase(
  private val groupStockShareListCase: GroupStockShareListCase
) : SelectStockStrategy {
  override fun execute(stockShareList: List<StockShare>, filter: StockFilter): SelectedStock? {
    return groupStockShareListCase.transform(stockShareList).minBy {
      selectMinByFilter(filter, it)
    }?.let {
      SelectedStock(
        it.code,
        it.getBalanceDescription(),
        it.getVariationDescription(),
        it.getVariation()
      )
    }
  }

  private fun selectMinByFilter(filter: StockFilter, item: StockShare): Double {
    return when (filter.rankingQualifier) {
      RankingQualifier.Balance -> item.getBalance()
      RankingQualifier.Percent -> item.getVariation()
    }
  }
}