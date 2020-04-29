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
	private val groupStockShareListCase	: GroupStockShareListCase,

	private val saveStockShareCase		: SaveStockShareCase,
	private val sellStockShareCase		: SellStockShareCase,
	private val editStockShareValueCase	: EditStockShareValueCase,
	private val deleteStockShareCase	: DeleteStockShareCase
) : BaseViewModel() {

	private val originalStockList 	= mutableListOf<StockShare>()
	private var groupedStockList 	: List<StockShare>? = null
	private var currentStock 		: StockShare? = null
	private var isShowingOriginal	= true

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
			is StockShareInteraction.List.ChangeListFilter			-> applyStockShareListFilterInteraction(
				interaction.groupShares
			)
			is StockShareInteraction.List.OpenStockShareDetails		-> openStockShareInteraction(interaction.item)
			is StockShareInteraction.AddPosition					-> addStockPositionInteraction(
				interaction.code,
				interaction.amount,
				interaction.price
			)
			is StockShareInteraction.UpdateStockPrice				-> updateStockPriceInteraction(
				interaction.code,
				interaction.amount,
				interaction.purchasePrice,
				interaction.currentPrice
			)
			is StockShareInteraction.DeleteStockShare				-> deleteCurrentStockShareInteraction()
		}
	}

	private suspend fun loadStockShareListInteraction() {
		_shareList.changeToListLoadingState()

		when(val result = getStockShareListCase.execute()){
			is Result.Success -> {
				computeStockScore(result.result)

				organizeStockListBeforeShowIt(result.result)
			}
			is Result.Failure -> {
				_shareList.changeToErrorState(result.throwable)
			}
		}
	}

	private fun openStockShareInteraction(item: StockShare) {
		_currentStockShare.value = item
		currentStock = item
	}

	private suspend fun addStockPositionInteraction(code: String, amount: Long, price: Double) {
		// Save new position
		saveStockShareCase.execute(
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

	private suspend fun deleteCurrentStockShareInteraction() {
		// Delete position
		currentStock?.run {
			deleteStockShareCase.execute(this)
		}

		// Send command to get back
		sendCommand(StockShareCommand.Back.FromEdit)
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
		editStockShareValueCase.execute(
			shareCode = code,
			value = currentPrice
		)

		// Send command to get back
		sendCommand(StockShareCommand.Back.FromEdit)
	}

	private fun applyStockShareListFilterInteraction(groupShares: Boolean) {
		// TODO create filter flow later.
		isShowingOriginal = !isShowingOriginal
		if(isShowingOriginal){
			originalStockList?.run {
				_shareList.changeToSuccessState(this)
			}
		}
		else{
			_shareList.changeToSuccessState(groupedStockList ?:  groupStockShareListCase.execute(originalStockList).apply {
				groupedStockList = this
			})
		}
	}

	private fun organizeStockListBeforeShowIt(stockShareList : List<StockShare>) {
		originalStockList.clear()
		originalStockList.addAll(stockShareList)
		groupedStockList = null

		if(isShowingOriginal){
			_shareList.changeToSuccessState(stockShareList)
		}
		else{
			groupedStockList = groupStockShareListCase.execute(stockShareList).apply {
				_shareList.changeToSuccessState(this)
			}
		}
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
