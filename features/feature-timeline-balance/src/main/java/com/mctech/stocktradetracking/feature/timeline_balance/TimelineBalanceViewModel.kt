package com.mctech.stocktradetracking.feature.timeline_balance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.stocktradetracking.domain.timeline_balance.entity.TimelineBalance
import com.mctech.stocktradetracking.domain.timeline_balance.interaction.*

class TimelineBalanceViewModel constructor(
	private val createPeriodCase: CreatePeriodCase,
	private val editPeriodCase: EditPeriodCase,
	private val getListOfPeriodsBalanceCase: GetListOfPeriodsBalanceCase,
	private val getPeriodTransactionsCase: GetPeriodTransactionsCase,
	private val depositMoneyCase: DepositMoneyCase,
	private val withdrawMoneyCase: WithdrawMoneyCase
) : BaseViewModel() {

	private val _periodList : MutableLiveData<ComponentState<List<TimelineBalance>>> = MutableLiveData(ComponentState.Initializing)
	val periodList : LiveData<ComponentState<List<TimelineBalance>>> = _periodList

}
