package com.mctech.stocktradetracking.domain.timeline_balance

sealed class TimelineBalanceError : RuntimeException(){
    object DuplicatePeriodException         : TimelineBalanceError()
    object UnknownExceptionException        : TimelineBalanceError()
}