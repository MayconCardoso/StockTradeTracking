package com.mctech.stocktradetracking.testing.data_factory.factories

import com.mctech.stocktradetracking.domain.stock_share_filter.entity.FilterSort
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.RankingQualifier
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter

object StockShareFilterDataFactory {
  fun single(
    isToGroup: Boolean = true,
    sort: FilterSort = FilterSort.NameAsc,
    ranking: RankingQualifier = RankingQualifier.Percent
  ) = StockFilter(
    isToGroup,
    sort,
    ranking
  )
}