package com.mctech.stocktradetracking.feature.stock_share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.architecture.mvvm.x.core.ktx.changeToErrorState
import com.mctech.architecture.mvvm.x.core.ktx.changeToListLoadingState
import com.mctech.architecture.mvvm.x.core.ktx.changeToLoadingState
import com.mctech.architecture.mvvm.x.core.ktx.changeToSuccessState
import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShareFinalBalance
import com.mctech.stocktradetracking.domain.stock_share.interaction.*
import kotlinx.coroutines.async
import java.util.*

class StockShareViewModel constructor(
	private val getStockShareListCase	: GetStockShareListCase,
	private val getFinalBalanceCase		: GetFinalBalanceCase,
	private val getWorstStockShareCase	: GetWorstStockShareCase,
	private val getBestStockShareCase	: GetBestStockShareCase,

	private val buyStockShareCase		: BuyStockShareCase,
	private val sellStockShareCase		: SellStockShareCase,
	private val editStockShareValueCase	: EditStockShareValueCase

) : BaseViewModel() {

	private val _shareList : MutableLiveData<ComponentState<List<StockShare>>> = MutableLiveData(ComponentState.Initializing)
	val shareList : LiveData<ComponentState<List<StockShare>>> = _shareList

	private val _stockShareFinalBalance : MutableLiveData<ComponentState<StockShareFinalBalance>> = MutableLiveData(ComponentState.Initializing)
	val stockShareFinalBalance : LiveData<ComponentState<StockShareFinalBalance>> = _stockShareFinalBalance

	private val _bestStockShare : MutableLiveData<ComponentState<StockShare>> = MutableLiveData(ComponentState.Initializing)
	val bestStockShare : LiveData<ComponentState<StockShare>> = _bestStockShare

	private val _worstStockShare : MutableLiveData<ComponentState<StockShare>> = MutableLiveData(ComponentState.Initializing)
	val worstStockShare : LiveData<ComponentState<StockShare>> = _worstStockShare

	private val _currentStockShare : MutableLiveData<StockShare> = MutableLiveData()
	val currentStockShare : LiveData<StockShare> = _currentStockShare

	override suspend fun handleUserInteraction(interaction: UserInteraction) {
		when(interaction){
			is StockShareInteraction.List.LoadStockShare 			-> loadStockShareListInteraction()
			is StockShareInteraction.List.OpenStockShareDetails		-> openStockShareInteraction(interaction.item)
			is StockShareInteraction.AddPosition					-> addStockPositionInteraction(
				interaction.code,
				interaction.amount,
				interaction.price
			)
			is StockShareInteraction.UpdateStockPrice				-> updateStockPriceInteraction(
				interaction.code,
				interaction.price
			)
		}
	}

	private suspend fun loadStockShareListInteraction() {
		_shareList.changeToListLoadingState()

		when(val result = getStockShareListCase.execute()){
			is Result.Success -> {
				computeStockScore(result.result)
				_shareList.changeToSuccessState(result.result)
			}
			is Result.Failure -> {
				_shareList.changeToErrorState(result.throwable)
			}
		}
	}

	private fun openStockShareInteraction(item: StockShare) {
		_currentStockShare.value = item
	}

	private suspend fun addStockPositionInteraction(code: String, amount: Int, price: Double) {
		// Save new position
		buyStockShareCase.execute(
			StockShare(
				code = code.toUpperCase(Locale.getDefault()),
				shareAmount = amount,
				purchasePrice = price,
				purchaseDate = Calendar.getInstance().time
			)
		)

		// Update list
		loadStockShareListInteraction()

		// Send command to get back
		sendCommand(StockShareCommand.Back.FromBuy)
	}

	private suspend fun updateStockPriceInteraction(code: String, price: Double) {
		// Update stock price
		editStockShareValueCase.execute(
			shareCode = code,
			value = price
		)

		// Update list
		loadStockShareListInteraction()

		// Send command to get back
		sendCommand(StockShareCommand.Back.FromEdit)
	}

	private fun computeStockScore(stockShareList: List<StockShare>) {
		viewModelScope.async { computeFinalBalance(stockShareList) }
		viewModelScope.async { computeBestStock(stockShareList) }
		viewModelScope.async { computeWorstStock(stockShareList) }
	}

	private fun computeFinalBalance(stockShareList: List<StockShare>) {
		_stockShareFinalBalance.changeToLoadingState()
		_stockShareFinalBalance.changeToSuccessState(
			getFinalBalanceCase.execute(stockShareList)
		)
	}

	private fun computeWorstStock(stockShareList: List<StockShare>) {
		_worstStockShare.changeToLoadingState()
		_worstStockShare.changeToSuccessState(
			getWorstStockShareCase.execute(stockShareList)
		)
	}

	private fun computeBestStock(stockShareList: List<StockShare>) {
		_bestStockShare.changeToLoadingState()
		_bestStockShare.changeToSuccessState(
			getBestStockShareCase.execute(stockShareList)
		)
	}

}
