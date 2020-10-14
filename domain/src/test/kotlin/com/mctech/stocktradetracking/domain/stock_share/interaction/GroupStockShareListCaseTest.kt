package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GroupStockShareListCaseTest {

  private lateinit var useCase: GroupStockShareListCase

  @Before
  fun `before each test`() {
    useCase = GroupStockShareListCase()
  }

  @Test
  fun `should group list by code`() = testScenario(
    action = {
      useCase.transform(StockShareDataFactory.ungroupedList())
    },
    assertions = { it ->
      assertThat(it.size).isEqualTo(3)

      // Sqia3 validaton
      val sqia = it.find { it.code == "SQIA3" }
      assertThat(sqia?.getInvestmentValue()).isEqualTo(100.0)
      assertThat(sqia?.shareAmount).isEqualTo(100)
      assertThat(sqia?.purchasePrice).isEqualTo(1.0)
      assertThat(sqia?.salePrice).isEqualTo(1.1)

      // Weg3 validation
      val weg3 = it.find { it.code == "WEGE3" }
      assertThat(weg3?.getInvestmentValue()).isEqualTo(2200.0)
      assertThat(weg3?.shareAmount).isEqualTo(600)
      assertThat(weg3?.purchasePrice).isEqualTo(3.6666666666666665)
      assertThat(weg3?.salePrice).isEqualTo(2.0)

      val mglu = it.find { it.code == "MGLU3" }
      assertThat(mglu?.getInvestmentValue()).isEqualTo(5200.0)
      assertThat(mglu?.shareAmount).isEqualTo(700)
      assertThat(mglu?.purchasePrice).isEqualTo(7.428571428571429)
      assertThat(mglu?.salePrice).isEqualTo(10.0)
    }
  )
}