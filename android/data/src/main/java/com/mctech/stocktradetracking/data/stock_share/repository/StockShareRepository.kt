package com.mctech.stocktradetracking.data.stock_share.repository

import com.mctech.library.logger.Logger
import com.mctech.stocktradetracking.data.stock_share.datasource.LocalStockShareDataSource
import com.mctech.stocktradetracking.data.stock_share.datasource.RemoteStockShareDataSource
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.service.StockShareService

class StockShareRepository(
    private val logger: Logger,
    private val localDataSource: LocalStockShareDataSource,
    private val remoteDataSource: RemoteStockShareDataSource
) : StockShareService {
    override suspend fun getStockShareList() = localDataSource.getStockShareList()
    override suspend fun saveStockShare(share: StockShare) = localDataSource.saveStockShare(share)
    override suspend fun sellStockShare(share: StockShare) = localDataSource.sellStockShare(share)
    override suspend fun deleteStockShare(share: StockShare) = localDataSource.deleteStockShare(share)
    override suspend fun editStockShareValue(shareCode: String, value: Double) =
        localDataSource.editStockShareValue(
            shareCode,
            value
        )

    override suspend fun syncStockSharePrice() {
        val stockShare = localDataSource.getDistinctStockCode()

        for(stockCode in stockShare){
            try{
                val currentPrice = remoteDataSource.getCurrentStockSharePrice(stockCode)

                if(currentPrice.price != null){
                    localDataSource.editStockShareValue(
                        stockCode,
                        currentPrice.price
                    )
                }
            }catch (ex : Exception){
                logger.e(e = ex)
            }
        }
    }
}
