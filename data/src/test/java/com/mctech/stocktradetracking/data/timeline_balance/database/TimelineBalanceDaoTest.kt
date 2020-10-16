package com.mctech.stocktradetracking.data.timeline_balance.database

import com.mctech.stocktradetracking.data.database.AppDatabaseTest
import com.mctech.stocktradetracking.data.timeline_balance.mapper.toDatabaseEntity
import com.mctech.stocktradetracking.testing.data_factory.factories.TimelineBalanceFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Calendar

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class TimelineBalanceDaoTest : AppDatabaseTest() {
  @Test
  fun `should insert timeline`() = testScenario(
    scenario = {
      val timeline = TimelineBalanceFactory.single(
        periodTag = "Test",
        periodInvestment = 10000.0,
        periodProfit = 3000.0
      )

      database.timelineBalanceDao().save(timeline.toDatabaseEntity())
    },
    action = {
      database.timelineBalanceDao().loadLastPeriod()
    },
    assertions = { timeline ->
      assertThat(timeline).isNotNull
      assertThat(timeline?.id).isNotNull()
      assertThat(timeline?.periodTag).isEqualTo("Test")
      assertThat(timeline?.periodInvestment).isEqualTo(10000.0)
      assertThat(timeline?.periodProfit).isEqualTo(3000.0)
    }
  )

  @Test
  fun `should delete timeline`() = testScenario(
    scenario = {
      val timeline = TimelineBalanceFactory.single(
        periodTag = "Test",
        periodInvestment = 10000.0,
        periodProfit = 3000.0
      )

      val entity = timeline.toDatabaseEntity()

      database.timelineBalanceDao().save(entity)
      database.timelineBalanceDao().delete(entity)
    },
    action = {
      database.timelineBalanceDao().loadLastPeriod()
    },
    assertions = { timeline ->
      assertThat(timeline).isNull()
    }
  )

  @Test
  fun `should load all periods sorted`() = testScenario(
    scenario = {
      val calendar = Calendar.getInstance()

      val firstTimeline = TimelineBalanceFactory.single(
        id = 1,
        periodTag = "Test",
        periodInvestment = 10000.0,
        periodProfit = 3000.0,
        startDate = calendar.time
      )

      calendar.add(Calendar.DAY_OF_MONTH, 1)
      val lastTimeline = TimelineBalanceFactory.single(
        id = 2,
        periodTag = "Last",
        periodInvestment = 10000.0,
        periodProfit = 3000.0,
        startDate = calendar.time
      )

      database.timelineBalanceDao().save(lastTimeline.toDatabaseEntity())
      database.timelineBalanceDao().save(firstTimeline.toDatabaseEntity())
    },
    action = {
      database.timelineBalanceDao().loadListOfPeriodsBalance()
    },
    assertions = { timeline ->
      assertThat(timeline).isNotNull
      assertThat(timeline.size).isEqualTo(2)
      assertThat(timeline[0].periodTag).isEqualTo("Last")
      assertThat(timeline[1].periodTag).isEqualTo("Test")
    }
  )
}