package com.mctech.stocktradetracking.feature.stock_share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import java.util.*

class StockShareViewModel constructor(
	private val getStockShareListCase	: GetStockShareListCase,
	private val buyStockShareCase		: BuyStockShareCase,
	private val sellStockShareCase		: SellStockShareCase,
	private val editStockShareValueCase	: EditStockShareValueCase,
	private val getFinalBalanceCase		: GetFinalBalanceCase
) : BaseViewModel() {

	private val _shareList : MutableLiveData<ComponentState<List<StockShare>>> = MutableLiveData(ComponentState.Initializing)
	val shareList : LiveData<ComponentState<List<StockShare>>> = _shareList

	private val _stockShareFinalBalance : MutableLiveData<ComponentState<StockShareFinalBalance>> = MutableLiveData(ComponentState.Initializing)
	val stockShareFinalBalance : LiveData<ComponentState<StockShareFinalBalance>> = _stockShareFinalBalance

	override suspend fun handleUserInteraction(interaction: UserInteraction) {
		when(interaction){
			is StockShareInteraction.List.LoadStockShare 	-> loadStockShareListInteraction()
			is StockShareInteraction.AddPosition			-> addStockPositionInteraction(
				interaction.code,
				interaction.amount,
				interaction.price
			)
		}
	}

	private suspend fun loadStockShareListInteraction() {
		_shareList.changeToListLoadingState()

		when(val result = getStockShareListCase.execute()){
			is Result.Success -> {
				computeFinalBalance(result.result)
				_shareList.changeToSuccessState(result.result)
			}
			is Result.Failure -> {
				_shareList.changeToErrorState(result.throwable)
			}
		}
	}

	private suspend fun computeFinalBalance(stockShareList: List<StockShare>) {
		_stockShareFinalBalance.changeToLoadingState()
		_stockShareFinalBalance.changeToSuccessState(
			getFinalBalanceCase.execute(stockShareList)
		)
	}

	private suspend fun addStockPositionInteraction(code: String, amount: Int, price: Double) {
		// Save new position
		buyStockShareCase.execute(
			StockShare(
				code = code.toUpperCase(Locale.getDefault()),
				shareAmount = amount,
				purchasePrice = price,
				salePrice = price + 12.91,
				purchaseDate = Calendar.getInstance().time
			)
		)

		// Update list
		loadStockShareListInteraction()

		// Send command to get back
		sendCommand(StockShareCommand.BackFromAddPosition)
	}
}
