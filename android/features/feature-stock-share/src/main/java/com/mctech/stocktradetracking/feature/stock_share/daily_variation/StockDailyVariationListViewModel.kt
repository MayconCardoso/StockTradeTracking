package com.mctech.stocktradetracking.feature.stock_share.daily_variation

import androidx.lifecycle.viewModelScope
import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.*
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.ComputeBalanceStrategy
import com.mctech.stocktradetracking.feature.stock_share.list_position.StockShareListViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

open class StockDailyVariationListViewModel (
	observeStockListCase		: ObserveStockShareListCase,
	getMarketStatusCase			: GetMarketStatusCase,

	selectWorstStockShareCase	: SelectWorstStockShareCase,
	selectBestStockShareCase	: SelectBestStockShareCase,
	syncStockSharePriceCase		: SyncStockSharePriceCase,

	getFinalBalanceCase			: ComputeBalanceStrategy,
	groupStockShareListCase		: GroupStockShareListCase

) : StockShareListViewModel(
	observeStockListCase 		= observeStockListCase,
	getMarketStatusCase			= getMarketStatusCase,
	selectWorstStockShareCase 	= selectWorstStockShareCase,
	selectBestStockShareCase 	= selectBestStockShareCase,
	syncStockSharePriceCase 	= syncStockSharePriceCase,
	groupStockShareListCase 	= groupStockShareListCase,
	getFinalBalanceCase 		= getFinalBalanceCase
) {

	private var realtimeJob : Job? = null

	override suspend fun handleUserInteraction(interaction: UserInteraction) {
		when(interaction){
			is StockDailyVariationListInteraction.StartRealtimePosition -> {
				startRealtimePositionInteraction()
			}
			is StockDailyVariationListInteraction.StopRealtimePosition -> {
				stopRealtimePositionInteraction()
			}
			else -> {
				super.handleUserInteraction(interaction)
			}
		}
	}

	override fun stockSelector(stock : StockShare) : Double{
		return stock.getDailyVariationBalance()
	}

	private fun stopRealtimePositionInteraction() {
		realtimeJob?.cancel()
	}

	private suspend fun startRealtimePositionInteraction() {
		stopRealtimePositionInteraction()

		realtimeJob = viewModelScope.async {
			do{
				syncStockPriceInteraction()
				delay(4000)
			}while (isActive)
		}
	}

	override fun onCleared() {
		stopRealtimePositionInteraction()
		super.onCleared()
	}
}
