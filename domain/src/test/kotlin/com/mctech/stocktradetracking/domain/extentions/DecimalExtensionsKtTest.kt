package com.mctech.stocktradetracking.domain.extentions

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DecimalExtensionsKtTest {

  @Test
  fun `should format to brazilian currency`() {
    assertThat(0.0.formatBrazilianCurrency()).isEqualTo("R$0,00")
    assertThat((-100.0).formatBrazilianCurrency()).isEqualTo("-R$100,00")
    assertThat((1539129.20).formatBrazilianCurrency()).isEqualTo("R$1.539.129,20")
    assertThat((1.20211).formatBrazilianCurrency()).isEqualTo("R$1,20")
    assertThat((1.20911).formatBrazilianCurrency()).isEqualTo("R$1,21")
  }

  @Test
  fun `should round number`() {
    assertThat(0.0091.round()).isEqualTo(0.01)
    assertThat(0.0040.round()).isEqualTo(0.0)
    assertThat(10.0049.round(3)).isEqualTo(10.005)
    assertThat(10.004921.round(6)).isEqualTo(10.004921)
  }

  @Test
  fun `should format percent`() {
    assertThat(0.0091.round().toPercent()).isEqualTo("0.01%")
    assertThat(0.0040.round().toPercent()).isEqualTo("0.0%")
    assertThat(10.0049.round(3).toPercent()).isEqualTo("10.005%")
    assertThat(10.004921.round(6).toPercent()).isEqualTo("10.004921%")
  }
}