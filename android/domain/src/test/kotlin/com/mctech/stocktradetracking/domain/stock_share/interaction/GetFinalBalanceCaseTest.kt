package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFinalBalanceCaseTest{
    private val positiveList        = StockShareDataFactory.finalBalancePositiveList()
    private val negativeList        = StockShareDataFactory.finalBalanceNegativeList()
    private val neutralList         = StockShareDataFactory.finalBalanceNeutralList()
    private val emptyList           = listOf<StockShare>()
    private lateinit var useCase    : GetFinalBalanceCase

    @Before
    fun `before each test`() {
        useCase = GetFinalBalanceCase()
    }

    @Test
    fun `should compute position balance`() = testScenario(
        action = {
            useCase.execute(positiveList)
        },
        assertions = {
            Assertions.assertThat(it.balance).isEqualTo(400.0)
            Assertions.assertThat(it.investment).isEqualTo(2400.0)
            Assertions.assertThat(it.getInvestmentDescription()).isEqualTo("R$2.400,00")
            Assertions.assertThat(it.getBalanceDescription()).isEqualTo("R$400,00")
            Assertions.assertThat(it.variation).isEqualTo(16.67)
            Assertions.assertThat(it.getVariationDescription()).isEqualTo("16.67%")
        }
    )

    @Test
    fun `should compute negative balance`() = testScenario(
        action = {
            useCase.execute(negativeList)
        },
        assertions = {
            Assertions.assertThat(it.balance).isEqualTo(-400.0)
            Assertions.assertThat(it.investment).isEqualTo(1600.0)
            Assertions.assertThat(it.getInvestmentDescription()).isEqualTo("R$1.600,00")
            Assertions.assertThat(it.getBalanceDescription()).isEqualTo("-R$400,00")
            Assertions.assertThat(it.variation).isEqualTo(-25.0)
            Assertions.assertThat(it.getVariationDescription()).isEqualTo("-25.0%")
        }
    )

    @Test
    fun `should compute neutral balance`() = testScenario(
        action = {
            useCase.execute(neutralList)
        },
        assertions = {
            Assertions.assertThat(it.balance).isEqualTo(0.0)
            Assertions.assertThat(it.investment).isEqualTo(2000.0)
            Assertions.assertThat(it.getInvestmentDescription()).isEqualTo("R$2.000,00")
            Assertions.assertThat(it.getBalanceDescription()).isEqualTo("R$0,00")
            Assertions.assertThat(it.variation).isEqualTo(0.0)
            Assertions.assertThat(it.getVariationDescription()).isEqualTo("0.0%")
        }
    )

    @Test
    fun `should compute empty balance`() = testScenario(
        action = {
            useCase.execute(emptyList)
        },
        assertions = {
            Assertions.assertThat(it.balance).isEqualTo(0.0)
            Assertions.assertThat(it.investment).isEqualTo(0.0)
            Assertions.assertThat(it.getInvestmentDescription()).isEqualTo("R$0,00")
            Assertions.assertThat(it.getBalanceDescription()).isEqualTo("R$0,00")
            Assertions.assertThat(it.variation).isEqualTo(0.0)
            Assertions.assertThat(it.getVariationDescription()).isEqualTo("0.0%")
        }
    )
}