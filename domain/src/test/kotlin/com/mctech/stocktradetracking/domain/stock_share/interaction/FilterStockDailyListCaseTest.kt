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
class FilterStockDailyListCaseTest {
    private lateinit var useCase: FilterStockDailyListCase

    @Before
    fun `before each test`() {
        useCase = FilterStockDailyListCase(GroupStockShareListCase())
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
            Assertions.assertThat(it[0].getDailyVariation()).isEqualTo(-80.0)
            Assertions.assertThat(it[1].getDailyVariation()).isEqualTo(-45.0)
            Assertions.assertThat(it[2].getDailyVariation()).isEqualTo(25.0)
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
            Assertions.assertThat(it[0].getDailyVariation()).isEqualTo(-80.0)
            Assertions.assertThat(it[1].getDailyVariation()).isEqualTo(-80.0)
            Assertions.assertThat(it[2].getDailyVariation()).isEqualTo(-45.0)
            Assertions.assertThat(it[3].getDailyVariation()).isEqualTo(25.0)
            Assertions.assertThat(it[4].getDailyVariation()).isEqualTo(25.0)
            Assertions.assertThat(it[5].getDailyVariation()).isEqualTo(25.0)
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
            Assertions.assertThat(it[0].getDailyVariation()).isEqualTo(25.0)
            Assertions.assertThat(it[1].getDailyVariation()).isEqualTo(-45.0)
            Assertions.assertThat(it[2].getDailyVariation()).isEqualTo(-80.0)
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
            Assertions.assertThat(it[0].getDailyVariation()).isEqualTo(25.0)
            Assertions.assertThat(it[1].getDailyVariation()).isEqualTo(25.0)
            Assertions.assertThat(it[2].getDailyVariation()).isEqualTo(25.0)
            Assertions.assertThat(it[3].getDailyVariation()).isEqualTo(-45.0)
            Assertions.assertThat(it[4].getDailyVariation()).isEqualTo(-80.0)
            Assertions.assertThat(it[5].getDailyVariation()).isEqualTo(-80.0)

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
            Assertions.assertThat(it[0].getDailyVariationBalance()).isEqualTo(-4800.0)
            Assertions.assertThat(it[1].getDailyVariationBalance()).isEqualTo(-90.0)
            Assertions.assertThat(it[2].getDailyVariationBalance()).isEqualTo(1400.0)
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
            Assertions.assertThat(it[0].getDailyVariationBalance()).isEqualTo(-3200.0)
            Assertions.assertThat(it[1].getDailyVariationBalance()).isEqualTo(-1600.0)
            Assertions.assertThat(it[2].getDailyVariationBalance()).isEqualTo(-90.0)
            Assertions.assertThat(it[3].getDailyVariationBalance()).isEqualTo(200.0)
            Assertions.assertThat(it[4].getDailyVariationBalance()).isEqualTo(400.0)
            Assertions.assertThat(it[5].getDailyVariationBalance()).isEqualTo(800.0)
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
            Assertions.assertThat(it[0].getDailyVariationBalance()).isEqualTo(1400.0)
            Assertions.assertThat(it[1].getDailyVariationBalance()).isEqualTo(-90.0)
            Assertions.assertThat(it[2].getDailyVariationBalance()).isEqualTo(-4800.0)
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

            Assertions.assertThat(it[0].getDailyVariationBalance()).isEqualTo(800.0)
            Assertions.assertThat(it[1].getDailyVariationBalance()).isEqualTo(400.0)
            Assertions.assertThat(it[2].getDailyVariationBalance()).isEqualTo(200.0)
            Assertions.assertThat(it[3].getDailyVariationBalance()).isEqualTo(-90.0)
            Assertions.assertThat(it[4].getDailyVariationBalance()).isEqualTo(-1600.0)
            Assertions.assertThat(it[5].getDailyVariationBalance()).isEqualTo(-3200.0)

        }
    )
}