package com.mctech.stocktradetracking.domain.timeline_balance

sealed class TimelineBalanceError : RuntimeException(){
    object UnknownExceptionException        : TimelineBalanceError()
}