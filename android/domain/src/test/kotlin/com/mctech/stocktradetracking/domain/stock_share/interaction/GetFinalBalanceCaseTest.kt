package com.mctech.stocktradetracking.domain.stock_share.interaction

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
            Assertions.assertThat(it.investment).isEqualTo(2000.0)
            Assertions.assertThat(it.getInvestmentDescription()).isEqualTo("R$2.000,00")
            Assertions.assertThat(it.getBalanceDescription()).isEqualTo("R$400,00")
            Assertions.assertThat(it.getVariant()).isEqualTo(20.0)
            Assertions.assertThat(it.getVariationDescription()).isEqualTo("20.0%")
        }
    )

    @Test
    fun `should compute negative balance`() = testScenario(
        action = {
            useCase.execute(negativeList)
        },
        assertions = {
            Assertions.assertThat(it.balance).isEqualTo(-400.0)
            Assertions.assertThat(it.investment).isEqualTo(2000.0)
            Assertions.assertThat(it.getInvestmentDescription()).isEqualTo("R$2.000,00")
            Assertions.assertThat(it.getBalanceDescription()).isEqualTo("-R$400,00")
            Assertions.assertThat(it.getVariant()).isEqualTo(-20.0)
            Assertions.assertThat(it.getVariationDescription()).isEqualTo("-20.0%")
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
            Assertions.assertThat(it.getVariant()).isEqualTo(0.0)
            Assertions.assertThat(it.getVariationDescription()).isEqualTo("0.0%")
        }
    )
}