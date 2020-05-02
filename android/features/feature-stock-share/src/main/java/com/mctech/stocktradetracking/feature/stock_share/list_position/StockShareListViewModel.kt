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
import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.stock_share.entity.MarketStatus
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShareFinalBalance
import com.mctech.stocktradetracking.domain.stock_share.interaction.*
import com.mctech.stocktradetracking.domain.stock_share.interaction.strategies.ComputeBalanceStrategy
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

open class StockShareListViewModel constructor(
	private val observeStockListCase		: ObserveStockShareListCase,
	private val getMarketStatusCase			: GetMarketStatusCase,
	private val selectWorstStockShareCase	: SelectWorstStockShareCase,
	private val selectBestStockShareCase	: SelectBestStockShareCase,
	private val syncStockSharePriceCase		: SyncStockSharePriceCase,

	private val groupStockShareListCase		: GroupStockShareListCase,
	private val getFinalBalanceCase			: ComputeBalanceStrategy
) : BaseViewModel() {

	private val originalStockList 	= mutableListOf<StockShare>()
	private var groupedStockList 	: List<StockShare>? = null
	private var isShowingOriginal	= true

	private val _shareList : MutableLiveData<ComponentState<List<StockShare>>> = MutableLiveData(ComponentState.Initializing)
	val shareList : LiveData<ComponentState<List<StockShare>>> = _shareList

	private val _stockShareFinalBalance : MutableLiveData<ComponentState<StockShareFinalBalance>> = MutableLiveData(ComponentState.Initializing)
	val stockShareFinalBalance : LiveData<ComponentState<StockShareFinalBalance>> = _stockShareFinalBalance

	private val _bestStockShare : MutableLiveData<ComponentState<StockShare?>> = MutableLiveData(ComponentState.Initializing)
	val bestStockShare : LiveData<ComponentState<StockShare?>> = _bestStockShare

	private val _worstStockShare : MutableLiveData<ComponentState<StockShare?>> = MutableLiveData(ComponentState.Initializing)
	val worstStockShare : LiveData<ComponentState<StockShare?>> = _worstStockShare

	private val _marketStatus : MutableLiveData<ComponentState<MarketStatus>> = MutableLiveData(ComponentState.Initializing)
	val marketStatus : LiveData<ComponentState<MarketStatus>> = _marketStatus

	override suspend fun handleUserInteraction(interaction: UserInteraction) {
		when(interaction){
			is StockShareListInteraction.LoadStockShare 	-> loadStockShareListInteraction()
			is StockShareListInteraction.SyncStockPrice 	-> syncStockPriceInteraction()
			is StockShareListInteraction.ChangeListFilter 	-> applyStockShareListFilterInteraction(
				interaction.groupShares
			)
		}
	}

	private suspend fun loadStockShareListInteraction() {
		loadMarketStatusInteraction()

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

	protected suspend fun syncStockPriceInteraction() {
		syncStockSharePriceCase.execute()
	}

	protected suspend fun loadMarketStatusInteraction(){
		_marketStatus.changeToLoadingState()
		when(val result = getMarketStatusCase.execute()){
			is Result.Success -> {
				_marketStatus.changeToSuccessState(result.result)
			}
			is Result.Failure -> {
				_marketStatus.changeToErrorState(result.throwable)
			}
		}
	}

	private fun applyStockShareListFilterInteraction(groupShares: Boolean) {
		// TODO create filter flow later.
		isShowingOriginal = !isShowingOriginal
		if(isShowingOriginal){
			originalStockList.run {
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
			selectWorstStockShareCase.execute(stockShareList){
				stockSelector(it)
			}
		)
	}

	private fun computeBestStock(stockShareList: List<StockShare>) {
		_bestStockShare.changeToLoadingState()
		_bestStockShare.changeToSuccessState(
			selectBestStockShareCase.execute(stockShareList){
				stockSelector(it)
			}
		)
	}

	open fun stockSelector(stock : StockShare) : Double{
		return stock.getBalance()
	}
}
