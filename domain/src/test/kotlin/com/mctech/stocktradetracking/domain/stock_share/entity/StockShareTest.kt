package com.mctech.stocktradetracking.domain.stock_share.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class StockShareTest{
    private val expectedDate = Calendar.getInstance().time
    private val expectedValue = StockShare(
        id = 1,
        code = "MGLU3",
        shareAmount = 200,
        purchasePrice = 27.92 ,
        salePrice = 50.65,
        purchaseDate = expectedDate,
        saleDate = expectedDate,
        isPositionOpened = true
    )

    private val expectedNegativeValue = StockShare(
        id = 1,
        code = "MGLU3",
        shareAmount = 200,
        purchasePrice = 45.03 ,
        salePrice = 40.47,
        purchaseDate = expectedDate,
        saleDate = expectedDate,
        isPositionOpened = true
    )

    private val expectedDefaultValue = StockShare(
        id = 1,
        code = "MGLU3",
        shareAmount = 200,
        purchasePrice = 27.92 ,
        purchaseDate = expectedDate
    )

    private val expectedEmpty = StockShare(
        id = 1,
        code = "MGLU3",
        shareAmount = 0,
        purchasePrice = 0.0 ,
        purchaseDate = expectedDate
    )

    @Test
    fun `should assert entity`(){
        assertThat(expectedValue.id).isEqualTo(1)
        assertThat(expectedValue.code).isEqualTo("MGLU3")
        assertThat(expectedValue.shareAmount).isEqualTo(200)
        assertThat(expectedValue.purchasePrice).isEqualTo(27.92)
        assertThat(expectedValue.salePrice).isEqualTo(50.65)
        assertThat(expectedValue.purchaseDate).isEqualTo(expectedDate)
        assertThat(expectedValue.saleDate).isEqualTo(expectedDate)
        assertThat(expectedValue.isPositionOpened).isEqualTo(true)
    }

    @Test
    fun `should assert default entity`(){
        assertThat(expectedDefaultValue.id).isEqualTo(1)
        assertThat(expectedDefaultValue.code).isEqualTo("MGLU3")
        assertThat(expectedDefaultValue.shareAmount).isEqualTo(200)
        assertThat(expectedDefaultValue.purchasePrice).isEqualTo(27.92)
        assertThat(expectedDefaultValue.salePrice).isEqualTo(27.92)
        assertThat(expectedDefaultValue.purchaseDate).isEqualTo(expectedDate)
        assertThat(expectedDefaultValue.saleDate).isNull()
        assertThat(expectedDefaultValue.isPositionOpened).isEqualTo(true)
    }

    @Test
    fun `should format buy description`(){
        assertThat(expectedValue.getBuyDescription()).isEqualTo("BUY 200 @ R$27,92")
        assertThat(expectedDefaultValue.getBuyDescription()).isEqualTo("BUY 200 @ R$27,92")
        assertThat(expectedNegativeValue.getBuyDescription()).isEqualTo("BUY 200 @ R$45,03")
        assertThat(expectedEmpty.getBuyDescription()).isEqualTo("BUY 0 @ R$0,00")
    }

    @Test
    fun `should format sell description`(){
        assertThat(expectedValue.getSellDescription()).isEqualTo("SELL 200 @ R$50,65")
        assertThat(expectedDefaultValue.getSellDescription()).isEqualTo("SELL 200 @ R$27,92")
        assertThat(expectedNegativeValue.getSellDescription()).isEqualTo("SELL 200 @ R$40,47")
        assertThat(expectedEmpty.getSellDescription()).isEqualTo("SELL 0 @ R$0,00")
    }

    @Test
    fun `should compute original price`(){
        assertThat(expectedValue.getOriginalStockPrice()).isEqualTo(5584.0)
        assertThat(expectedDefaultValue.getOriginalStockPrice()).isEqualTo(5584.0)
        assertThat(expectedNegativeValue.getOriginalStockPrice()).isEqualTo(9006.0)
        assertThat(expectedEmpty.getOriginalStockPrice()).isEqualTo(0.0)
    }

    @Test
    fun `should compute final price`(){
        assertThat(expectedValue.getFinalStockPrice()).isEqualTo(10130.0)
        assertThat(expectedDefaultValue.getFinalStockPrice()).isEqualTo(5584.0)
        assertThat(expectedNegativeValue.getFinalStockPrice()).isEqualTo(8094.0)
        assertThat(expectedEmpty.getFinalStockPrice()).isEqualTo(0.0)
    }

    @Test
    fun `should format original price description`(){
        assertThat(expectedValue.getOriginalStockPriceDescription()).isEqualTo("R$5.584,00")
        assertThat(expectedDefaultValue.getOriginalStockPriceDescription()).isEqualTo("R$5.584,00")
        assertThat(expectedNegativeValue.getOriginalStockPriceDescription()).isEqualTo("R$9.006,00")
        assertThat(expectedEmpty.getOriginalStockPriceDescription()).isEqualTo("R$0,00")
    }

    @Test
    fun `should format final price description`(){
        assertThat(expectedValue.getFinalStockPriceDescription()).isEqualTo("R$10.130,00")
        assertThat(expectedDefaultValue.getFinalStockPriceDescription()).isEqualTo("R$5.584,00")
        assertThat(expectedNegativeValue.getFinalStockPriceDescription()).isEqualTo("R$8.094,00")
        assertThat(expectedEmpty.getFinalStockPriceDescription()).isEqualTo("R$0,00")
    }

    @Test
    fun `should compute variation`(){
        assertThat(expectedValue.getVariation()).isEqualTo(81.41)
        assertThat(expectedDefaultValue.getVariation()).isEqualTo(0.0)
        assertThat(expectedNegativeValue.getVariation()).isEqualTo(-10.13)
        assertThat(expectedEmpty.getVariation()).isEqualTo(0.0)
    }

    @Test
    fun `should format variation description`(){
        assertThat(expectedValue.getVariationDescription()).isEqualTo("81.41%")
        assertThat(expectedDefaultValue.getVariationDescription()).isEqualTo("0.0%")
        assertThat(expectedNegativeValue.getVariationDescription()).isEqualTo("-10.13%")
        assertThat(expectedEmpty.getVariationDescription()).isEqualTo("0.0%")
    }

    @Test
    fun `should compute balance`(){
        assertThat(expectedValue.getBalance()).isEqualTo(4546.0)
        assertThat(expectedDefaultValue.getBalance()).isEqualTo(0.0)
        assertThat(expectedNegativeValue.getBalance()).isEqualTo(-912.0)
        assertThat(expectedEmpty.getBalance()).isEqualTo(0.0)
    }

    @Test
    fun `should format balance description`(){
        assertThat(expectedValue.getBalanceDescription()).isEqualTo("R$4.546,00")
        assertThat(expectedDefaultValue.getBalanceDescription()).isEqualTo("R$0,00")
        assertThat(expectedNegativeValue.getBalanceDescription()).isEqualTo("-R$912,00")
        assertThat(expectedEmpty.getBalanceDescription()).isEqualTo("R$0,00")
    }
}