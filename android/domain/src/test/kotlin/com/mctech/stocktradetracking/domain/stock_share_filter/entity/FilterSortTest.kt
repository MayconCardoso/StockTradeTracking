package com.mctech.stocktradetracking.domain.stock_share_filter.entity

import org.assertj.core.api.Assertions
import org.junit.Test

class FilterSortTest{
    @Test
    fun `should assert enum`(){
        Assertions.assertThat(FilterSort.NameAsc.id).isEqualTo(1)
        Assertions.assertThat(FilterSort.NameDesc.id).isEqualTo(2)
        Assertions.assertThat(FilterSort.PercentAsc.id).isEqualTo(3)
        Assertions.assertThat(FilterSort.PercentDesc.id).isEqualTo(4)
        Assertions.assertThat(FilterSort.BalanceAsc.id).isEqualTo(5)
        Assertions.assertThat(FilterSort.BalanceDesc.id).isEqualTo(6)
    }
}