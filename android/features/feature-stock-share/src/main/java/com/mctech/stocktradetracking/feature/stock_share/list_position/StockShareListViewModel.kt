package com.mctech.stocktradetracking.feature.stock_share.list_position

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
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShareFinalBalance
import com.mctech.stocktradetracking.domain.stock_share.interaction.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class StockShareListViewModel constructor(
	private val observeStockListCase	: ObserveStockShareListCase,
	private val getFinalBalanceCase		: GetFinalBalanceCase,
	private val getWorstStockShareCase	: GetWorstStockShareCase,
	private val getBestStockShareCase	: GetBestStockShareCase,
	private val groupStockShareListCase	: GroupStockShareListCase,
	private val syncStockSharePriceCase	: SyncStockSharePriceCase
) : BaseViewModel() {

	private val originalStockList 	= mutableListOf<StockShare>()
	private var groupedStockList 	: List<StockShare>? = null
	private var isShowingOriginal	= true

	private val _shareList : MutableLiveData<ComponentState<List<StockShare>>> = MutableLiveData(ComponentState.Initializing)
	val shareList : LiveData<ComponentState<List<StockShare>>> = _shareList

	private val _stockShareFinalBalance : MutableLiveData<ComponentState<StockShareFinalBalance>> = MutableLiveData(ComponentState.Initializing)
	val stockShareFinalBalance : LiveData<ComponentState<StockShareFinalBalance>> = _stockShareFinalBalance

	private val _bestStockShare : MutableLiveData<ComponentState<StockShare>> = MutableLiveData(ComponentState.Initializing)
	val bestStockShare : LiveData<ComponentState<StockShare>> = _bestStockShare

	private val _worstStockShare : MutableLiveData<ComponentState<StockShare>> = MutableLiveData(ComponentState.Initializing)
	val worstStockShare : LiveData<ComponentState<StockShare>> = _worstStockShare

	override suspend fun handleUserInteraction(interaction: UserInteraction) {
		when(interaction){
			is StockShareListInteraction.List.LoadStockShare 	-> loadStockShareListInteraction()
			is StockShareListInteraction.SyncStockPrice 		-> syncStockPriceInteraction()
			is StockShareListInteraction.List.ChangeListFilter 	-> applyStockShareListFilterInteraction(
				interaction.groupShares
			)
		}
	}

	private suspend fun loadStockShareListInteraction() {
		observeStockListCase.execute()
			.onStart {
				_shareList.changeToListLoadingState()
			}
			.catch { exception ->
				_shareList.changeToErrorState(exception)
			}.collect { result ->
				computeStockScore(result)
				organizeStockListBeforeShowIt(result)
			}
	}

	private suspend fun syncStockPriceInteraction() {
		syncStockSharePriceCase.execute()
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
