package com.mctech.stocksharetracking.feature.stock_share_filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.stocktradetracking.domain.stock_share_filter.entity.StockFilter
import com.mctech.stocktradetracking.domain.stock_share_filter.interaction.ObserveCurrentFilterCase
import com.mctech.stocktradetracking.domain.stock_share_filter.interaction.SaveStockShareFilterCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class StockShareFilterViewModel(
    private val saveStockShareFilterCase: SaveStockShareFilterCase,
    private val observeCurrentFilterCase: ObserveCurrentFilterCase
) : BaseViewModel(){

    val currentFilter : LiveData<ComponentState<StockFilter>> = liveData {
        observeCurrentFilterCase.execute()
            .onStart {
                emit(ComponentState.Loading.FromEmpty)
            }
            .catch {
                emit(ComponentState.Error(it))
            }
            .collect {
                emit(ComponentState.Success(it))
            }
    }

    override suspend fun handleUserInteraction(interaction: UserInteraction) {
        when(interaction){
            is StockShareFilterInteraction.ApplyFilter -> {
                saveStockShareFilterCase.execute(interaction.stockFilter)
                sendCommand(StockShareFilterCommand.NavigateBack)
            }
        }
    }
}