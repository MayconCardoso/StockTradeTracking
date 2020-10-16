package com.mctech.stocktradetracking.domain.stock_share_filter.entity

import org.assertj.core.api.Assertions
import org.junit.Test


class StockFilterTest {

  @Test
  fun `should create default instance`() {
    val defaultValue = StockFilter.Default()
    Assertions.assertThat(defaultValue.sort).isEqualTo(FilterSort.BalanceDesc)
    Assertions.assertThat(defaultValue.isGroupingStock).isEqualTo(true)
    Assertions.assertThat(defaultValue.rankingQualifier).isEqualTo(RankingQualifier.Balance)
  }

  @Test
  fun `should keep instance`() {
    val defaultValue = StockFilter(
      false,
      FilterSort.NameAsc,
      RankingQualifier.Percent
    )

    Assertions.assertThat(defaultValue.sort).isEqualTo(FilterSort.NameAsc)
    Assertions.assertThat(defaultValue.isGroupingStock).isEqualTo(false)
    Assertions.assertThat(defaultValue.rankingQualifier).isEqualTo(RankingQualifier.Percent)
  }
}