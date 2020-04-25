package com.mctech.stocktradetracking.feature.stock_share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.UserInteraction

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.GetStockShareListCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.BuyStockShareCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.SellStockShareCase
import com.mctech.stocktradetracking.domain.stock_share.interaction.EditStockShareValueCase

class StockShareViewModel constructor(
	private val getStockShareListCase	: GetStockShareListCase,
	private val buyStockShareCase		: BuyStockShareCase,
	private val sellStockShareCase		: SellStockShareCase,
	private val editStockShareValueCase	: EditStockShareValueCase
) : BaseViewModel() {

	private val _shareList : MutableLiveData<ComponentState<StockShare>> = MutableLiveData(ComponentState.Initializing)
	val shareList : LiveData<ComponentState<StockShare>> = _shareList

	override suspend fun handleUserInteraction(interaction: UserInteraction) {
		super.handleUserInteraction(interaction)
	}

}
