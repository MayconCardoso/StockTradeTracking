package com.mctech.stocktradetracking.testing.data_factory.factories

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import java.util.Calendar
import java.util.Date

object StockShareDataFactory {
  fun listOf(count: Int = 0): List<StockShare> {
    val list = mutableListOf<StockShare>()
    for (x in 0 until count) {
      list.add(single())
    }
    return list
  }

  fun single(
    id: Long = 0,
    code: String = "",
    shareAmount: Long = 100,
    purchasePrice: Double = 0.0,
    salePrice: Double = 0.0,
    purchaseDate: Date = Calendar.getInstance().time,
    saleDate: Date = Calendar.getInstance().time,
    isPositionOpened: Boolean = true
  ) = StockShare(
    id = id,
    code = code,
    shareAmount = shareAmount,
    purchasePrice = purchasePrice,
    purchaseDate = purchaseDate,
    salePrice = salePrice,
    saleDate = saleDate,
    isPositionOpened = isPositionOpened
  )

  fun finalBalancePositiveList(): List<StockShare> {
    return mutableListOf<StockShare>().apply {
      add(
        StockShare(
          code = "MGLU3",
          purchasePrice = 10.0,
          salePrice = 12.0,
          shareAmount = 100,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 2.0,
          previousClose = 8.0,
          isPositionOpened = true
        )
      )

      add(
        StockShare(
          code = "MGLU3",
          purchasePrice = 10.0,
          salePrice = 12.0,
          shareAmount = 100,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 2.0,
          previousClose = 8.0,
          isPositionOpened = true
        )
      )
    }
  }

  fun finalBalanceNegativeList(): List<StockShare> {
    return mutableListOf<StockShare>().apply {
      add(
        StockShare(
          code = "MGLU3",
          purchasePrice = 10.0,
          salePrice = 8.0,
          shareAmount = 100,
          purchaseDate = Calendar.getInstance().time,
          previousClose = 10.0,
          marketChange = 2.0,
          isPositionOpened = true
        )
      )

      add(
        StockShare(
          code = "MGLU3",
          purchasePrice = 10.0,
          salePrice = 8.0,
          shareAmount = 100,
          purchaseDate = Calendar.getInstance().time,
          previousClose = 10.0,
          marketChange = 2.0,
          isPositionOpened = true
        )
      )
    }
  }

  fun finalBalanceNeutralList(): List<StockShare> {
    return mutableListOf<StockShare>().apply {
      add(
        StockShare(
          code = "MGLU3",
          purchasePrice = 10.0,
          salePrice = 10.0,
          shareAmount = 100,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 0.0,
          previousClose = 10.0,
          isPositionOpened = true
        )
      )

      add(
        StockShare(
          code = "MGLU3",
          purchasePrice = 10.0,
          salePrice = 10.0,
          shareAmount = 100,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 0.0,
          previousClose = 10.0,
          isPositionOpened = true
        )
      )
    }
  }

  fun ungroupedList(): List<StockShare> {
    return mutableListOf<StockShare>().apply {
      add(
        StockShare(
          code = "MGLU3",
          purchasePrice = 2.0,
          salePrice = 10.0,
          shareAmount = 100,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 2.0,
          previousClose = 8.0,
          isPositionOpened = true
        )
      )

      add(
        StockShare(
          code = "MGLU3",
          purchasePrice = 5.0,
          salePrice = 10.0,
          shareAmount = 400,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 2.0,
          previousClose = 8.0,
          isPositionOpened = true
        )
      )

      add(
        StockShare(
          code = "MGLU3",
          purchasePrice = 15.0,
          salePrice = 10.0,
          shareAmount = 200,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 2.0,
          previousClose = 8.0,
          isPositionOpened = true
        )
      )

      add(
        StockShare(
          code = "WEGE3",
          purchasePrice = 5.0,
          salePrice = 2.0,
          shareAmount = 400,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 0.0,
          previousClose = 10.0,
          isPositionOpened = true
        )
      )

      add(
        StockShare(
          code = "WEGE3",
          purchasePrice = 1.0,
          salePrice = 2.0,
          shareAmount = 200,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 0.0,
          previousClose = 10.0,
          isPositionOpened = true
        )
      )

      add(
        StockShare(
          code = "SQIA3",
          purchasePrice = 1.0,
          salePrice = 1.1,
          shareAmount = 100,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 0.0,
          previousClose = 2.0,
          isPositionOpened = true
        )
      )
    }
  }

  fun simpleList(): List<StockShare> {
    return mutableListOf<StockShare>().apply {
      add(
        StockShare(
          code = "MGLU3",
          purchasePrice = 2.0,
          salePrice = 10.0,
          shareAmount = 100,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 2.0,
          previousClose = 8.0,
          isPositionOpened = true
        )
      )

      add(
        StockShare(
          code = "WEGE3",
          purchasePrice = 1.0,
          salePrice = 2.0,
          shareAmount = 200,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 0.0,
          previousClose = 10.0,
          isPositionOpened = true
        )
      )

      add(
        StockShare(
          code = "SQIA3",
          purchasePrice = 1.0,
          salePrice = 1.1,
          shareAmount = 100,
          purchaseDate = Calendar.getInstance().time,
          marketChange = 0.0,
          previousClose = 2.0,
          isPositionOpened = true
        )
      )
    }
  }

  fun splitList(): List<StockShare> {
    return mutableListOf<StockShare>().apply {
      add(
        StockShare(
          code = "MGLU3",
          purchasePrice = 10.0,
          salePrice = 8.0,
          shareAmount = 100,
          purchaseDate = Calendar.getInstance().time,
          previousClose = 10.0,
          marketChange = 2.0,
          isPositionOpened = true
        )
      )

      add(
        StockShare(
          code = "MGLU3",
          purchasePrice = 20.0,
          salePrice = 8.0,
          shareAmount = 200,
          purchaseDate = Calendar.getInstance().time,
          previousClose = 10.0,
          marketChange = 2.0,
          isPositionOpened = true
        )
      )
    }
  }

}