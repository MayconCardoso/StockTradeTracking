package com.mctech.stocktradetracking.testing.data_factory.factories

import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import java.util.*

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
        shareAmount: Int = 100,
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
                    purchaseDate =  Calendar.getInstance().time
                )
            )

            add(
                StockShare(
                    code = "MGLU3",
                    purchasePrice = 10.0,
                    salePrice = 12.0,
                    shareAmount = 100,
                    purchaseDate =  Calendar.getInstance().time
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
                    purchaseDate =  Calendar.getInstance().time
                )
            )

            add(
                StockShare(
                    code = "MGLU3",
                    purchasePrice = 10.0,
                    salePrice = 8.0,
                    shareAmount = 100,
                    purchaseDate =  Calendar.getInstance().time
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
                    purchaseDate =  Calendar.getInstance().time
                )
            )

            add(
                StockShare(
                    code = "MGLU3",
                    purchasePrice = 10.0,
                    salePrice = 10.0,
                    shareAmount = 100,
                    purchaseDate =  Calendar.getInstance().time
                )
            )
        }
    }
}