package com.mctech.stocktradetracking.domain.stock_share.entity

import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * @author MAYCON CARDOSO on 03/05/20.
 */
class MarketStatusTest{

    @Test
    fun `should test entity`(){
        val marketStatus = MarketStatus("AA", true)
        Assertions.assertThat(marketStatus.message).isEqualTo("AA")
        Assertions.assertThat(marketStatus.isOperation).isTrue()
    }
}