package com.mctech.stocktradetracking.domain.stock_share.interaction.strategies

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.GroupStockShareListCase
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.FilterSort
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter

abstract class FilterStockListStrategy(
  private val groupStockShareListCase: GroupStockShareListCase
) {
  fun execute(stockShareList: List<StockShare>, filter: StockFilter): List<StockShare> {
    val resolvedList = if (filter.isGroupingStock)
      groupStockShareListCase.transform(stockShareList)
    else
      stockShareList

    return sort(resolvedList, filter.sort)
  }

  protected abstract fun sort(
    stockShareList: List<StockShare>,
    filterSort: FilterSort
  ): List<StockShare>
}