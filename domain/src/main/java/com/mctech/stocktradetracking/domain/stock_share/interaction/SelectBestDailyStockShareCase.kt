package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.SelectedStock
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.SelectStockStrategy
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.RankingQualifier
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter

class SelectBestDailyStockShareCase(
  private val groupStockShareListCase: GroupStockShareListCase
) : SelectStockStrategy {

  override fun execute(stockShareList: List<StockShare>, filter: StockFilter): SelectedStock? {
    return groupStockShareListCase.transform(stockShareList).maxByOrNull {
      selectMaxByFilter(filter, it)
    }?.let {
      SelectedStock(
        it.code,
        it.getDailyVariationBalanceDescription(),
        it.getDailyVariationDescription(),
        it.getDailyVariationBalance()
      )
    }
  }

  private fun selectMaxByFilter(filter: StockFilter, item: StockShare): Double {
    return when (filter.rankingQualifier) {
      RankingQualifier.Balance -> item.getDailyVariationBalance()
      RankingQualifier.Percent -> item.getDailyVariation()
    }
  }
}