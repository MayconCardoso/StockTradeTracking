package com.mctech.stocktradetracking.feature.stock_share.edit_position

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.architecture.mvvm.x.core.ktx.changeToSuccessState
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.DeleteStockShareCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.EditStockSharePriceCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.SaveStockShareCase

class StockShareEditPositionViewModel constructor(
    private val saveStockShareCase		: SaveStockShareCase,
    private val editStockSharePriceCase	: EditStockSharePriceCase,
    private val deleteStockShareCase	: DeleteStockShareCase
) : BaseViewModel() {
	private var currentStock 		: StockShare? = null

	private val _currentStockShare 	: MutableLiveData<ComponentState<StockShare>> = MutableLiveData(ComponentState.Initializing)
	val currentStockShare : LiveData<ComponentState<StockShare>> = _currentStockShare

	override suspend fun handleUserInteraction(interaction: UserInteraction) {
		when(interaction){
			is StockShareEditPositionInteraction.OpenStockShareDetails -> openStockShareInteraction(
				interaction.item
			)
			is StockShareEditPositionInteraction.UpdateStockPrice -> updateStockPriceInteraction(
				interaction.code,
				interaction.amount,
				interaction.purchasePrice,
				interaction.currentPrice
			)
			is StockShareEditPositionInteraction.DeleteStockShare -> deleteCurrentStockShareInteraction()
		}
	}

	private fun openStockShareInteraction(item: StockShare) {
		_currentStockShare.changeToSuccessState(item)
		currentStock = item
	}

	private suspend fun deleteCurrentStockShareInteraction() {
		// Delete position
		currentStock?.run {
			deleteStockShareCase.execute(this)
		}

		// Send command to get back
		sendCommand(StockShareEditPositionCommand.NavigateBack)
	}

	private suspend fun updateStockPriceInteraction(
		code: String,
		amount: Long,
		purchasePrice: Double,
		currentPrice: Double
	) {
		// Save item
		currentStock?.run {
			this.code = code
			this.shareAmount = amount
			this.purchasePrice = purchasePrice

			saveStockShareCase.execute(this)
		}

		// Update stock price
		editStockSharePriceCase.execute(
			shareCode = code,
			value = currentPrice
		)

		// Send command to get back
		sendCommand(StockShareEditPositionCommand.NavigateBack)
	}
}
