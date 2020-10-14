package com.mctech.stocktradetracking.domain.stock_share_filter.entity

import org.assertj.core.api.Assertions
import org.junit.Test

class RankingQualifierTest {
  @Test
  fun `should assert enum`() {
    Assertions.assertThat(RankingQualifier.Balance.id).isEqualTo(1)
    Assertions.assertThat(RankingQualifier.Percent.id).isEqualTo(2)
  }
}