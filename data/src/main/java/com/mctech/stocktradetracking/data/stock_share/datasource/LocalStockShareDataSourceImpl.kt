package com.mctech.stocktradetracking.data.stock_share.datasource

import com.instacart.library.truetime.TrueTime
import com.mctech.stocktradetracking.data.stock_share.database.StockShareDao
import com.mctech.stocktradetracking.data.stock_share.mapper.toDatabaseEntity
import com.mctech.stocktradetracking.domain.stock_share.entity.MarketStatus
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.TimeZone

class LocalStockShareDataSourceImpl(
  private val stockShareDao: StockShareDao
) : LocalStockShareDataSource {

  override fun observeStockShareList(): Flow<List<StockShare>> {
    return stockShareDao.observeAllOpenedPosition()
  }

  override fun observeStockClosedList(): Flow<List<StockShare>> {
    return stockShareDao.observeStockClosedList()
  }

  override suspend fun getMarketStatus(): MarketStatus {
    val currentDate = Calendar.getInstance().apply {
      time = TrueTime.now()
      timeZone = TimeZone.getTimeZone("GMT-3") // Brazilian timezone.
    }

    when (currentDate.get(Calendar.DAY_OF_WEEK)) {
      Calendar.SUNDAY,
      Calendar.SATURDAY -> {
        return MarketStatus(
          "Ibovespa is closed.", false
        )
      }
    }

    return when (currentDate.get(Calendar.HOUR_OF_DAY)) {
      in 10..17 -> {
        MarketStatus("Ibovespa is opened", true)
      }
      else -> {
        MarketStatus("Ibovespa is closed.", false)
      }
    }
  }

  override suspend fun getAllByCode(code: String): List<StockShare> {
    return stockShareDao.loadAllStockSharesByCode(code)
  }

  override suspend fun getDistinctStockCode(): List<String> {
    return stockShareDao.loadDistinctStockCodes()
  }

  override suspend fun saveStockShare(share: StockShare) {
    val databaseEntity = share.toDatabaseEntity()

    // Try to load the same position to update the right price.s
    stockShareDao.loadStockSharePosition(share.code)?.let {
      databaseEntity.salePrice = it.salePrice
      databaseEntity.marketChange = it.marketChange
      databaseEntity.previousClose = it.previousClose
    }

    stockShareDao.save(databaseEntity)
  }

  override suspend fun sellStockShare(share: StockShare) {
    stockShareDao.save(share.toDatabaseEntity())
  }

  override suspend fun deleteStockShare(share: StockShare) {
    stockShareDao.delete(share.toDatabaseEntity())
  }

  override suspend fun closeStockShare(share: StockShare) {
    stockShareDao.closeStockShare(share.id ?: 0, share.salePrice)
  }

  override suspend fun editStockShareValue(
    shareCode: String,
    currentPrice: Double,
    marketChange: Double?,
    previousClose: Double?
  ) {
    if (previousClose != null && marketChange != null) {
      stockShareDao.editStockSharePriceAutomatically(
        shareCode,
        currentPrice,
        marketChange,
        previousClose
      )
    } else {
      stockShareDao.editStockSharePriceManually(shareCode, currentPrice)
    }
  }
}