package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ObserveStockShareListCaseTest{
    private val service         = mock<StockShareService>()
    private lateinit var useCase: ObserveStockShareListCase

    @Before
    fun `before each test`() {
        useCase = ObserveStockShareListCase(service)
    }

    @Test
    fun `should delegate calling`() = testScenario(
        action = {
            useCase.execute()
        },
        assertions = {
            verify(service).observeStockShareList()
            verifyNoMoreInteractions(service)
        }
    )
}