package com.mctech.stocktradetracking.domain.timeline_balance.entity

import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.*

/**
 * @author MAYCON CARDOSO on 03/05/20.
 */
class TimelineBalanceTest{
    private val date = Calendar.getInstance().time
    private val expectedSingle = TimelineBalance(
        id = 1,
        periodProfit = 1000.0,
        periodInvestment = 10000.0,
        periodTag =  "First",
        startDate = date
    )

    private val expectedNeutral = TimelineBalance(
        id = 3,
        periodProfit = 0.0,
        periodInvestment = 0.0,
        periodTag =  "Neutral",
        startDate = date
    )

    private val expectedLinked = TimelineBalance(
        id = 2,
        periodProfit = -3000.0,
        periodInvestment = 20000.0,
        periodTag =  "Second",
        parent = expectedSingle,
        parentPeriodId = 1,
        startDate = date
    )

    @Test
    fun `should assert entity`(){
        Assertions.assertThat(expectedSingle.id).isEqualTo(1)
        Assertions.assertThat(expectedSingle.periodProfit).isEqualTo(1000.0)
        Assertions.assertThat(expectedSingle.periodInvestment).isEqualTo(10000.0)
        Assertions.assertThat(expectedSingle.periodTag).isEqualTo("First")
        Assertions.assertThat(expectedSingle.parent).isNull()
        Assertions.assertThat(expectedSingle.parentPeriodId).isNull()
        Assertions.assertThat(expectedSingle.startDate).isEqualTo(date)

        Assertions.assertThat(expectedLinked.id).isEqualTo(2)
        Assertions.assertThat(expectedLinked.periodProfit).isEqualTo(-3000.0)
        Assertions.assertThat(expectedLinked.periodInvestment).isEqualTo(20000.0)
        Assertions.assertThat(expectedLinked.periodTag).isEqualTo("Second")
        Assertions.assertThat(expectedLinked.parent).isEqualTo(expectedSingle)
        Assertions.assertThat(expectedLinked.parentPeriodId).isEqualTo(1)
        Assertions.assertThat(expectedLinked.startDate).isEqualTo(date)
    }

    @Test
    fun `should compute final investment balance`(){
        Assertions.assertThat(expectedSingle.getFinalInvestmentBalance()).isEqualTo(10000.0)
        Assertions.assertThat(expectedLinked.getFinalInvestmentBalance()).isEqualTo(30000.0)
    }

    @Test
    fun `should compute final profit`(){
        Assertions.assertThat(expectedSingle.getFinalProfit()).isEqualTo(1000.0)
        Assertions.assertThat(expectedLinked.getFinalProfit()).isEqualTo(-2000.0)
        Assertions.assertThat(expectedNeutral.getPeriodVariation()).isEqualTo(0.0)
    }

    @Test
    fun `should compute final balance`(){
        Assertions.assertThat(expectedSingle.getFinalBalance()).isEqualTo(11000.0)
        Assertions.assertThat(expectedLinked.getFinalBalance()).isEqualTo(28000.0)
    }

    @Test
    fun `should compute period variation`(){
        Assertions.assertThat(expectedSingle.getPeriodVariation()).isEqualTo(9.09)
        Assertions.assertThat(expectedLinked.getPeriodVariation()).isEqualTo(-27.27)
    }

    @Test
    fun `should format final investment balance`(){
        Assertions.assertThat(expectedSingle.getFinalInvestmentBalanceDescription()).isEqualTo("R$10.000,00")
        Assertions.assertThat(expectedLinked.getFinalInvestmentBalanceDescription()).isEqualTo("R$30.000,00")
    }

    @Test
    fun `should format period investment`(){
        Assertions.assertThat(expectedSingle.getPeriodInvestmentDescription()).isEqualTo("R$10.000,00")
        Assertions.assertThat(expectedLinked.getPeriodInvestmentDescription()).isEqualTo("R$20.000,00")
    }

    @Test
    fun `should format period profit`(){
        Assertions.assertThat(expectedSingle.getPeriodProfitDescription()).isEqualTo("R$1.000,00")
        Assertions.assertThat(expectedLinked.getPeriodProfitDescription()).isEqualTo("-R$3.000,00")
    }

    @Test
    fun `should format final profit`(){
        Assertions.assertThat(expectedSingle.getFinalProfitDescription()).isEqualTo("R$1.000,00")
        Assertions.assertThat(expectedLinked.getFinalProfitDescription()).isEqualTo("-R$2.000,00")
    }

    @Test
    fun `should format final balance`(){
        Assertions.assertThat(expectedSingle.getFinalBalanceDescription()).isEqualTo("R$11.000,00")
        Assertions.assertThat(expectedLinked.getFinalBalanceDescription()).isEqualTo("R$28.000,00")
    }

    @Test
    fun `should format period variation`(){
        Assertions.assertThat(expectedSingle.getPeriodVariationDescription()).isEqualTo("9.09%")
        Assertions.assertThat(expectedLinked.getPeriodVariationDescription()).isEqualTo("-27.27%")
    }

    @Test
    fun `should compute profit by final balance`(){
        Assertions.assertThat(expectedSingle.computeProfitByFinalBalance(300000.0)).isEqualTo(290000.0)
        Assertions.assertThat(expectedLinked.computeProfitByFinalBalance(300000.0)).isEqualTo(269000.0)
    }
}