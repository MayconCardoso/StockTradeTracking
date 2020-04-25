package com.mctech.stocktradetracking.data.stock_share.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "stock_share",
    indices = [
        Index(
            value = ["code"],
            name = "index_stock_share_code"
        ),
        Index(
            value = ["isPositionOpened"],
            name = "index_stock_share_position_closed"
        )
    ]
)
data class StockShareDatabaseEntity(
    @PrimaryKey
    val id: Long,
    val code : String,
    val shareAmount : Int,
    val purchasePrice : Double,
    val salePrice : Double = purchasePrice,
    val purchaseDate : Date,
    val saleDate : Date? = null,
    val isPositionOpened : Boolean = true
)