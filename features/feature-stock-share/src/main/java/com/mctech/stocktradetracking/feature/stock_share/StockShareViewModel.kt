package com.mctech.stocktradetracking.feature.stock_share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.architecture.mvvm.x.core.ktx.changeToErrorState
import com.mctech.architecture.mvvm.x.core.ktx.changeToListLoadingState
import com.mctech.architecture.mvvm.x.core.ktx.changeToSuccessState
import com.mctech.stocktradetracking.domain.Result
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.BuyStockShareCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.EditStockShareValueCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.GetStockShareListCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.SellStockShareCase
import kotlinx.coroutines.delay

class StockShareViewModel constructor(
	private val getStockShareListCase	: GetStockShareListCase,
	private val buyStockShareCase		: BuyStockShareCase,
	private val sellStockShareCase		: SellStockShareCase,
	private val editStockShareValueCase	: EditStockShareValueCase
) : BaseViewModel() {

	private val _shareList : MutableLiveData<ComponentState<List<StockShare>>> = MutableLiveData(ComponentState.Initializing)
	val shareList : LiveData<ComponentState<List<StockShare>>> = _shareList

	override suspend fun handleUserInteraction(interaction: UserInteraction) {
		when(interaction){
			is StockShareInteraction.List.LoadStockShare -> loadStockShareListInteraction()
		}
	}

	private suspend fun loadStockShareListInteraction() {
		_shareList.changeToListLoadingState()

		delay(2000)
		when(val result = getStockShareListCase.execute()){
			is Result.Success -> {
				_shareList.changeToSuccessState(result.result)
			}
			is Result.Failure -> {
				_shareList.changeToErrorState(result.throwable)
			}
		}
	}
}
