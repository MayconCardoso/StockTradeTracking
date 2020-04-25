package com.mctech.stocktradetracking.domain.stock_share

sealed class StockShareError : RuntimeException(){
    object InvalidSharePriceException    : StockShareError()
    object ShareCodeNotFoundException    : StockShareError()
    object UnknownExceptionException     : StockShareError()
}