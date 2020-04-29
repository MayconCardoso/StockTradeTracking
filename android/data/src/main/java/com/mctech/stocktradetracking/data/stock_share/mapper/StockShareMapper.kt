package com.mctech.stocktradetracking.data.stock_share.mapper

import com.mctech.stocktradetracking.data.stock_share.database.StockShareDatabaseEntity
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare

fun StockShare.toDatabaseEntity() = StockShareDatabaseEntity(
    id = id,
    code = code,
    shareAmount = shareAmount,
    purchasePrice = purchasePrice,
    salePrice = salePrice,
    purchaseDate = purchaseDate,
    saleDate = saleDate,
    isPositionOpened = isPositionOpened
)

fun StockShareDatabaseEntity.toBusinessEntity() = StockShare(
    id = id,
    code = code,
    shareAmount = shareAmount,
    purchasePrice = purchasePrice,
    salePrice = salePrice,
    purchaseDate = purchaseDate,
    saleDate = saleDate,
    isPositionOpened = isPositionOpened
)