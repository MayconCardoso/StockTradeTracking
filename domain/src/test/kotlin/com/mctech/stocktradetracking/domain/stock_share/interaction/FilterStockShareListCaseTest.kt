package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share_filter.entity.FilterSort
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.RankingQualifier
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FilterStockShareListCaseTest {
  private lateinit var useCase: FilterStockShareListCase

  @Before
  fun `before each test`() {
    useCase = FilterStockShareListCase(GroupStockShareListCase())
  }

  @Test
  fun `should sort by name asc grouped`() = testScenario(
    action = {
      useCase.execute(
        StockShareDataFactory.ungroupedList(),
        StockFilter(
          isGroupingStock = true,
          sort = FilterSort.NameAsc,
          rankingQualifier = RankingQualifier.Balance
        )
      )
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(3)
      Assertions.assertThat(it[0].code).isEqualTo("MGLU3")
      Assertions.assertThat(it[1].code).isEqualTo("SQIA3")
      Assertions.assertThat(it[2].code).isEqualTo("WEGE3")
    }
  )

  @Test
  fun `should sort by name asc ungrouped`() = testScenario(
    action = {
      useCase.execute(
        StockShareDataFactory.ungroupedList(),
        StockFilter(
          isGroupingStock = false,
          sort = FilterSort.NameAsc,
          rankingQualifier = RankingQualifier.Balance
        )
      )
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(6)
      Assertions.assertThat(it[0].code).isEqualTo("MGLU3")
      Assertions.assertThat(it[1].code).isEqualTo("MGLU3")
      Assertions.assertThat(it[2].code).isEqualTo("MGLU3")
      Assertions.assertThat(it[3].code).isEqualTo("SQIA3")
      Assertions.assertThat(it[4].code).isEqualTo("WEGE3")
      Assertions.assertThat(it[5].code).isEqualTo("WEGE3")
    }
  )

  @Test
  fun `should sort by name desc grouped`() = testScenario(
    action = {
      useCase.execute(
        StockShareDataFactory.ungroupedList(),
        StockFilter(
          isGroupingStock = true,
          sort = FilterSort.NameDesc,
          rankingQualifier = RankingQualifier.Balance
        )
      )
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(3)
      Assertions.assertThat(it[0].code).isEqualTo("WEGE3")
      Assertions.assertThat(it[1].code).isEqualTo("SQIA3")
      Assertions.assertThat(it[2].code).isEqualTo("MGLU3")
    }
  )

  @Test
  fun `should sort by name desc ungrouped`() = testScenario(
    action = {
      useCase.execute(
        StockShareDataFactory.ungroupedList(),
        StockFilter(
          isGroupingStock = false,
          sort = FilterSort.NameDesc,
          rankingQualifier = RankingQualifier.Balance
        )
      )
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(6)
      Assertions.assertThat(it[0].code).isEqualTo("WEGE3")
      Assertions.assertThat(it[1].code).isEqualTo("WEGE3")
      Assertions.assertThat(it[2].code).isEqualTo("SQIA3")
      Assertions.assertThat(it[3].code).isEqualTo("MGLU3")
      Assertions.assertThat(it[4].code).isEqualTo("MGLU3")
      Assertions.assertThat(it[5].code).isEqualTo("MGLU3")
    }
  )


  @Test
  fun `should sort by percent asc grouped`() = testScenario(
    action = {
      useCase.execute(
        StockShareDataFactory.ungroupedList(),
        StockFilter(
          isGroupingStock = true,
          sort = FilterSort.PercentAsc,
          rankingQualifier = RankingQualifier.Balance
        )
      )
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(3)
      Assertions.assertThat(it[0].getVariation()).isEqualTo(-45.45)
      Assertions.assertThat(it[1].getVariation()).isEqualTo(10.0)
      Assertions.assertThat(it[2].getVariation()).isEqualTo(34.62)
    }
  )

  @Test
  fun `should sort by percent asc ungrouped`() = testScenario(
    action = {
      useCase.execute(
        StockShareDataFactory.ungroupedList(),
        StockFilter(
          isGroupingStock = false,
          sort = FilterSort.PercentAsc,
          rankingQualifier = RankingQualifier.Balance
        )
      )
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(6)
      Assertions.assertThat(it[0].getVariation()).isEqualTo(-60.0)
      Assertions.assertThat(it[1].getVariation()).isEqualTo(-33.33)
      Assertions.assertThat(it[2].getVariation()).isEqualTo(10.0)
      Assertions.assertThat(it[3].getVariation()).isEqualTo(100.0)
      Assertions.assertThat(it[4].getVariation()).isEqualTo(100.0)
      Assertions.assertThat(it[5].getVariation()).isEqualTo(400.0)
    }
  )

  @Test
  fun `should sort by percent desc grouped`() = testScenario(
    action = {
      useCase.execute(
        StockShareDataFactory.ungroupedList(),
        StockFilter(
          isGroupingStock = true,
          sort = FilterSort.PercentDesc,
          rankingQualifier = RankingQualifier.Balance
        )
      )
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(3)
      Assertions.assertThat(it[0].getVariation()).isEqualTo(34.62)
      Assertions.assertThat(it[1].getVariation()).isEqualTo(10.0)
      Assertions.assertThat(it[2].getVariation()).isEqualTo(-45.45)
    }
  )

  @Test
  fun `should sort by percent desc ungrouped`() = testScenario(
    action = {
      useCase.execute(
        StockShareDataFactory.ungroupedList(),
        StockFilter(
          isGroupingStock = false,
          sort = FilterSort.PercentDesc,
          rankingQualifier = RankingQualifier.Balance
        )
      )
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(6)
      Assertions.assertThat(it[0].getVariation()).isEqualTo(400.0)
      Assertions.assertThat(it[1].getVariation()).isEqualTo(100.0)
      Assertions.assertThat(it[2].getVariation()).isEqualTo(100.0)
      Assertions.assertThat(it[3].getVariation()).isEqualTo(10.0)
      Assertions.assertThat(it[4].getVariation()).isEqualTo(-33.33)
      Assertions.assertThat(it[5].getVariation()).isEqualTo(-60.0)

    }
  )

  @Test
  fun `should sort by balance asc grouped`() = testScenario(
    action = {
      useCase.execute(
        StockShareDataFactory.ungroupedList(),
        StockFilter(
          isGroupingStock = true,
          sort = FilterSort.BalanceAsc,
          rankingQualifier = RankingQualifier.Balance
        )
      )
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(3)
      Assertions.assertThat(it[0].getBalance()).isEqualTo(-1000.0)
      Assertions.assertThat(it[1].getBalance()).isEqualTo(10.0)
      Assertions.assertThat(it[2].getBalance()).isEqualTo(1800.0)
    }
  )

  @Test
  fun `should sort by balance asc ungrouped`() = testScenario(
    action = {
      useCase.execute(
        StockShareDataFactory.ungroupedList(),
        StockFilter(
          isGroupingStock = false,
          sort = FilterSort.BalanceAsc,
          rankingQualifier = RankingQualifier.Balance
        )
      )
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(6)

      Assertions.assertThat(it[0].getBalance()).isEqualTo(-1200.0)
      Assertions.assertThat(it[1].getBalance()).isEqualTo(-1000.0)
      Assertions.assertThat(it[2].getBalance()).isEqualTo(10.0)
      Assertions.assertThat(it[3].getBalance()).isEqualTo(200.0)
      Assertions.assertThat(it[4].getBalance()).isEqualTo(800.0)
      Assertions.assertThat(it[5].getBalance()).isEqualTo(2000.0)
    }
  )

  @Test
  fun `should sort by balance desc grouped`() = testScenario(
    action = {
      useCase.execute(
        StockShareDataFactory.ungroupedList(),
        StockFilter(
          isGroupingStock = true,
          sort = FilterSort.BalanceDesc,
          rankingQualifier = RankingQualifier.Balance
        )
      )
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(3)
      Assertions.assertThat(it[0].getBalance()).isEqualTo(1800.0)
      Assertions.assertThat(it[1].getBalance()).isEqualTo(10.0)
      Assertions.assertThat(it[2].getBalance()).isEqualTo(-1000.0)
    }
  )

  @Test
  fun `should sort by balance desc ungrouped`() = testScenario(
    action = {
      useCase.execute(
        StockShareDataFactory.ungroupedList(),
        StockFilter(
          isGroupingStock = false,
          sort = FilterSort.BalanceDesc,
          rankingQualifier = RankingQualifier.Balance
        )
      )
    },
    assertions = {
      Assertions.assertThat(it.size).isEqualTo(6)

      Assertions.assertThat(it[0].getBalance()).isEqualTo(2000.0)
      Assertions.assertThat(it[1].getBalance()).isEqualTo(800.0)
      Assertions.assertThat(it[2].getBalance()).isEqualTo(200.0)
      Assertions.assertThat(it[3].getBalance()).isEqualTo(10.0)
      Assertions.assertThat(it[4].getBalance()).isEqualTo(-1000.0)
      Assertions.assertThat(it[5].getBalance()).isEqualTo(-1200.0)

    }
  )
}