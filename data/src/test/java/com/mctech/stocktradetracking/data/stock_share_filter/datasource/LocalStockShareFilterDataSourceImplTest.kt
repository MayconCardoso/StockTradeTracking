package com.mctech.stocktradetracking.data.stock_share_filter.datasource

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import com.mctech.stocktradetracking.testing.data_factory.factories.StockShareFilterDataFactory
import com.mctech.stocktradetracking.testing.data_factory.testFlowScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@FlowPreview
@RunWith(RobolectricTestRunner::class)
class LocalStockShareFilterDataSourceImplTest {
    private val expectedSingle = StockShareFilterDataFactory.single()
    private lateinit var preference: SharedPreferences

    private lateinit var dataSource: LocalStockShareFilterDataSource

    @Before
    fun `before each test`() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        preference = app.getSharedPreferences("Teste", Context.MODE_PRIVATE)

        dataSource = LocalStockShareFilterDataSourceImpl(
            preference, Gson()
        )
    }

    @Test
    fun `should restore empty`() = testFlowScenario(
        scenario = {
            preference.edit().clear()
        },
        observe = {
            dataSource.observeStockShareFilter()
        },
        assertions = {
            Assertions.assertThat(it).isEmpty()
        }
    )

    @Test
    fun `should restore last`() = testFlowScenario(
        scenario = {
            preference
                .edit()
                .putString(
                    LocalStockShareFilterDataSourceImpl.PREFERENCE_KEY,
                    Gson().toJson(expectedSingle)
                )
                .apply()
        },
        observe = {
            dataSource.observeStockShareFilter()
        },
        assertions = {
            Assertions.assertThat(it).isNotEmpty
        }
    )

    @Test
    fun `should save filter`() = testFlowScenario(
        observe = {
            dataSource.observeStockShareFilter()
        },
        action = {
            dataSource.saveFilter(expectedSingle)
        },
        assertions = {
            Assertions.assertThat(it).isNotEmpty
            Assertions.assertThat(it.first()).isEqualTo(expectedSingle)
        }
    )
}