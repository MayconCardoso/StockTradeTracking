package com.mctech.stocktradetracking.domain.stock_share.interaction

import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService
import com.mctech.stocktradetracking.testing.data_factory.testScenario
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test

class ObserveStockClosedListCaseTest{
    private val service         = mock<StockShareService>()
    private lateinit var useCase: ObserveStockClosedListCase

    @Before
    fun `before each test`() {
        useCase = ObserveStockClosedListCase(service)
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