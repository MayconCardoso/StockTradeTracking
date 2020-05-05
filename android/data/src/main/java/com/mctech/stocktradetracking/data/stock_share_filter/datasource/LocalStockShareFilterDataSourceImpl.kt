package com.mctech.stocktradetracking.data.stock_share_filter.datasource

import android.content.SharedPreferences
import com.google.gson.Gson
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ExperimentalCoroutinesApi
class LocalStockShareFilterDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : LocalStockShareFilterDataSource{

    companion object{
        private const val PREFERENCE_KEY = "currentFilter"
    }

    private val filterFlow by lazy {
        ConflatedBroadcastChannel(
            getCurrentFilter()
        )
    }

    override suspend fun observeStockShareFilter() = filterFlow.asFlow()

    override suspend fun saveFilter(stockFilter: StockFilter) {
        // Save new filter
        saveCurrentFilter(stockFilter)

        // Send it to observers.
        filterFlow.send(stockFilter)
    }

    private fun saveCurrentFilter(stockFilter: StockFilter) {
        sharedPreferences
            .edit()
            .putString(PREFERENCE_KEY, gson.toJson(stockFilter))
            .apply()
    }

    private fun getCurrentFilter(): StockFilter? {
        return sharedPreferences.getString(PREFERENCE_KEY, null)?.let {
            gson.fromJson(it, StockFilter::class.java)
        }
    }
}