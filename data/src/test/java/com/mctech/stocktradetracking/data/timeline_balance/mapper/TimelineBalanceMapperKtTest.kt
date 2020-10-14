package com.mctech.stocktradetracking.data.timeline_balance.mapper

import com.mctech.stocktradetracking.data.timeline_balance.database.TimelineBalanceDatabaseEntity
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import org.assertj.core.api.Assertions
import org.junit.Test

class TimelineBalanceMapperKtTest {

  @Test
  fun `should map to database entity`() {
    val parent = TimelineBalance(
      id = 1,
      periodTag = "MGLU3",
      periodProfit = 45.89,
      periodInvestment = 45.89
    )

    val share = TimelineBalance(
      id = 2,
      periodTag = "MGLU3",
      parentPeriodId = 1L,
      parent = parent,
      periodProfit = 45.89,
      periodInvestment = 45.89
    )

    val target = share.toDatabaseEntity()

    Assertions.assertThat(share.id).isEqualTo(target.id)
    Assertions.assertThat(share.periodTag).isEqualTo(target.periodTag)
    Assertions.assertThat(share.parentPeriodId).isEqualTo(target.parentPeriodId)
    Assertions.assertThat(share.parent?.id).isEqualTo(target.parentPeriodId)
    Assertions.assertThat(share.periodProfit).isEqualTo(target.periodProfit)
    Assertions.assertThat(share.periodInvestment).isEqualTo(target.periodInvestment)
  }

  @Test
  fun `should map to business entity`() {
    val parent = TimelineBalanceDatabaseEntity(
      id = 1,
      periodTag = "MGLU3",
      periodProfit = 45.89,
      periodInvestment = 45.89
    )

    val share = TimelineBalanceDatabaseEntity(
      id = 2,
      periodTag = "MGLU3",
      parentPeriodId = 1L,
      periodProfit = 45.89,
      periodInvestment = 45.89
    )

    val target = share.toBusinessEntity()

    Assertions.assertThat(share.id).isEqualTo(target.id)
    Assertions.assertThat(share.periodTag).isEqualTo(target.periodTag)
    Assertions.assertThat(share.parentPeriodId).isEqualTo(target.parentPeriodId)
    Assertions.assertThat(share.periodProfit).isEqualTo(target.periodProfit)
    Assertions.assertThat(share.periodInvestment).isEqualTo(target.periodInvestment)
  }
}