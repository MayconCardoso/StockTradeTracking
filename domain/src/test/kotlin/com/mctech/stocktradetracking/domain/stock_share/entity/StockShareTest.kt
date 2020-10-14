package com.mctech.stocktradetracking.domain.stock_share.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.Calendar

class StockShareTest {
  private val expectedDate = Calendar.getInstance().time
  private val expectedValue = StockShare(
    id = 1,
    code = "MGLU3",
    shareAmount = 200,
    purchasePrice = 27.92,
    salePrice = 50.65,
    purchaseDate = expectedDate,
    saleDate = expectedDate,
    isPositionOpened = true,
    previousClose = 48.10,
    marketChange = 2.0
  )

  private val expectedNegativeValue = StockShare(
    id = 1,
    code = "MGLU3",
    shareAmount = 200,
    purchasePrice = 45.03,
    salePrice = 40.47,
    purchaseDate = expectedDate,
    saleDate = expectedDate,
    isPositionOpened = true,
    previousClose = 44.10,
    marketChange = 2.0
  )

  private val expectedDefaultValue = StockShare(
    id = 1,
    code = "MGLU3",
    shareAmount = 200,
    purchasePrice = 27.92,
    purchaseDate = expectedDate,
    previousClose = 0.0
  )

  private val expectedEmpty = StockShare(
    id = 1,
    code = "MGLU3",
    shareAmount = 0,
    purchasePrice = 0.0,
    purchaseDate = expectedDate
  )

  @Test
  fun `should assert entity`() {
    assertThat(expectedValue.id).isEqualTo(1)
    assertThat(expectedValue.code).isEqualTo("MGLU3")
    assertThat(expectedValue.shareAmount).isEqualTo(200)
    assertThat(expectedValue.purchasePrice).isEqualTo(27.92)
    assertThat(expectedValue.salePrice).isEqualTo(50.65)
    assertThat(expectedValue.purchaseDate).isEqualTo(expectedDate)
    assertThat(expectedValue.saleDate).isEqualTo(expectedDate)
    assertThat(expectedValue.isPositionOpened).isEqualTo(true)
    assertThat(expectedValue.previousClose).isEqualTo(48.1)
    assertThat(expectedValue.marketChange).isEqualTo(2.0)
  }

  @Test
  fun `should assert default entity`() {
    assertThat(expectedDefaultValue.id).isEqualTo(1)
    assertThat(expectedDefaultValue.code).isEqualTo("MGLU3")
    assertThat(expectedDefaultValue.shareAmount).isEqualTo(200)
    assertThat(expectedDefaultValue.purchasePrice).isEqualTo(27.92)
    assertThat(expectedDefaultValue.salePrice).isEqualTo(27.92)
    assertThat(expectedDefaultValue.purchaseDate).isEqualTo(expectedDate)
    assertThat(expectedDefaultValue.saleDate).isNull()
    assertThat(expectedDefaultValue.isPositionOpened).isEqualTo(true)
    assertThat(expectedDefaultValue.previousClose).isEqualTo(0.0)
    assertThat(expectedDefaultValue.marketChange).isNull()
  }

  @Test
  fun `should format buy description`() {
    assertThat(expectedValue.getBuyDescription()).isEqualTo("BUY 200 @ R$27,92")
    assertThat(expectedDefaultValue.getBuyDescription()).isEqualTo("BUY 200 @ R$27,92")
    assertThat(expectedNegativeValue.getBuyDescription()).isEqualTo("BUY 200 @ R$45,03")
    assertThat(expectedEmpty.getBuyDescription()).isEqualTo("BUY 0 @ R$0,00")
  }

  @Test
  fun `should format sell description`() {
    assertThat(expectedValue.getSellDescription()).isEqualTo("SELL 200 @ R$50,65")
    assertThat(expectedDefaultValue.getSellDescription()).isEqualTo("SELL 200 @ R$27,92")
    assertThat(expectedNegativeValue.getSellDescription()).isEqualTo("SELL 200 @ R$40,47")
    assertThat(expectedEmpty.getSellDescription()).isEqualTo("SELL 0 @ R$0,00")
  }

  @Test
  fun `should compute original price`() {
    assertThat(expectedValue.getInvestmentValue()).isEqualTo(5584.0)
    assertThat(expectedDefaultValue.getInvestmentValue()).isEqualTo(5584.0)
    assertThat(expectedNegativeValue.getInvestmentValue()).isEqualTo(9006.0)
    assertThat(expectedEmpty.getInvestmentValue()).isEqualTo(0.0)
  }

  @Test
  fun `should compute final price`() {
    assertThat(expectedValue.getFinalStockPrice()).isEqualTo(10130.0)
    assertThat(expectedDefaultValue.getFinalStockPrice()).isEqualTo(5584.0)
    assertThat(expectedNegativeValue.getFinalStockPrice()).isEqualTo(8094.0)
    assertThat(expectedEmpty.getFinalStockPrice()).isEqualTo(0.0)
  }

  @Test
  fun `should format original price description`() {
    assertThat(expectedValue.getInvestmentValueDescription()).isEqualTo("R$5.584,00")
    assertThat(expectedDefaultValue.getInvestmentValueDescription()).isEqualTo("R$5.584,00")
    assertThat(expectedNegativeValue.getInvestmentValueDescription()).isEqualTo("R$9.006,00")
    assertThat(expectedEmpty.getInvestmentValueDescription()).isEqualTo("R$0,00")
  }

  @Test
  fun `should format final price description`() {
    assertThat(expectedValue.getFinalStockPriceDescription()).isEqualTo("R$10.130,00")
    assertThat(expectedDefaultValue.getFinalStockPriceDescription()).isEqualTo("R$5.584,00")
    assertThat(expectedNegativeValue.getFinalStockPriceDescription()).isEqualTo("R$8.094,00")
    assertThat(expectedEmpty.getFinalStockPriceDescription()).isEqualTo("R$0,00")
  }

  @Test
  fun `should compute variation`() {
    assertThat(expectedValue.getVariation()).isEqualTo(81.41)
    assertThat(expectedDefaultValue.getVariation()).isEqualTo(0.0)
    assertThat(expectedNegativeValue.getVariation()).isEqualTo(-10.13)
    assertThat(expectedEmpty.getVariation()).isEqualTo(0.0)
  }

  @Test
  fun `should format variation description`() {
    assertThat(expectedValue.getVariationDescription()).isEqualTo("81.41%")
    assertThat(expectedDefaultValue.getVariationDescription()).isEqualTo("0.0%")
    assertThat(expectedNegativeValue.getVariationDescription()).isEqualTo("-10.13%")
    assertThat(expectedEmpty.getVariationDescription()).isEqualTo("0.0%")
  }

  @Test
  fun `should compute balance`() {
    assertThat(expectedValue.getBalance()).isEqualTo(4546.0)
    assertThat(expectedDefaultValue.getBalance()).isEqualTo(0.0)
    assertThat(expectedNegativeValue.getBalance()).isEqualTo(-912.0)
    assertThat(expectedEmpty.getBalance()).isEqualTo(0.0)
  }

  @Test
  fun `should format balance description`() {
    assertThat(expectedValue.getBalanceDescription()).isEqualTo("R$4.546,00")
    assertThat(expectedDefaultValue.getBalanceDescription()).isEqualTo("R$0,00")
    assertThat(expectedNegativeValue.getBalanceDescription()).isEqualTo("-R$912,00")
    assertThat(expectedEmpty.getBalanceDescription()).isEqualTo("R$0,00")
  }

  @Test
  fun `should format previous close description`() {
    assertThat(expectedValue.getPreviousCloseDescription()).isEqualTo("Previous Close R$48,10")
    assertThat(expectedDefaultValue.getPreviousCloseDescription()).isEqualTo("Previous Close R$0,00")
    assertThat(expectedNegativeValue.getPreviousCloseDescription()).isEqualTo("Previous Close R$44,10")
    assertThat(expectedEmpty.getPreviousCloseDescription()).isEqualTo("Previous Close R$0,00")
  }

  @Test
  fun `should format current price description`() {
    assertThat(expectedValue.getCurrentPriceDescription()).isEqualTo("Current Price R$50,65")
    assertThat(expectedDefaultValue.getCurrentPriceDescription()).isEqualTo("Current Price R$27,92")
    assertThat(expectedNegativeValue.getCurrentPriceDescription()).isEqualTo("Current Price R$40,47")
    assertThat(expectedEmpty.getCurrentPriceDescription()).isEqualTo("Current Price R$0,00")
  }

  @Test
  fun `compute daily variation`() {
    assertThat(expectedValue.getDailyVariation()).isEqualTo(5.3)
    assertThat(expectedDefaultValue.getDailyVariation()).isEqualTo(0.0)
    assertThat(expectedNegativeValue.getDailyVariation()).isEqualTo(-8.23)
    assertThat(expectedEmpty.getDailyVariation()).isEqualTo(0.0)
  }

  @Test
  fun `format daily variation description`() {
    assertThat(expectedValue.getDailyVariationDescription()).isEqualTo("5.3%")
    assertThat(expectedDefaultValue.getDailyVariationDescription()).isEqualTo("0.0%")
    assertThat(expectedNegativeValue.getDailyVariationDescription()).isEqualTo("-8.23%")
    assertThat(expectedEmpty.getDailyVariationDescription()).isEqualTo("0.0%")
  }

  @Test
  fun `compute daily variation balance`() {
    assertThat(expectedValue.getDailyVariationBalance()).isEqualTo(510.0)
    assertThat(expectedDefaultValue.getDailyVariationBalance()).isEqualTo(0.0)
    assertThat(expectedNegativeValue.getDailyVariationBalance()).isEqualTo(-726.0)
    assertThat(expectedEmpty.getDailyVariationBalance()).isEqualTo(0.0)
  }

  @Test
  fun `format daily variation balance description`() {
    assertThat(expectedValue.getDailyVariationBalanceDescription()).isEqualTo("R$510,00")
    assertThat(expectedDefaultValue.getDailyVariationBalanceDescription()).isEqualTo("R$0,00")
    assertThat(expectedNegativeValue.getDailyVariationBalanceDescription()).isEqualTo("-R$726,00")
    assertThat(expectedEmpty.getDailyVariationBalanceDescription()).isEqualTo("R$0,00")
  }

}