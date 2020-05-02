package com.mctech.stocktradetracking.feature.stock_share.daily_variation

import android.util.Log
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
	selectWorstStockShareCase	: SelectWorstStockShareCase,
	selectBestStockShareCase	: SelectBestStockShareCase,
	syncStockSharePriceCase		: SyncStockSharePriceCase,

	getFinalBalanceCase			: ComputeBalanceStrategy,
	groupStockShareListCase		: GroupStockShareListCase

) : StockShareListViewModel(
	observeStockListCase 		= observeStockListCase,
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
		Log.i("REALTIIIIIIME", "Paroooou")
		realtimeJob?.cancel()
	}

	private suspend fun startRealtimePositionInteraction() {
		stopRealtimePositionInteraction()
		Log.i("REALTIIIIIIME", "Iniciou")

		realtimeJob = viewModelScope.async {
			do{
				syncStockPriceInteraction()
				Log.i("REALTIIIIIIME", "Passou")
				delay(5000)
			}while (isActive)
		}
	}

	override fun onCleared() {
		stopRealtimePositionInteraction()
		super.onCleared()
	}
}
