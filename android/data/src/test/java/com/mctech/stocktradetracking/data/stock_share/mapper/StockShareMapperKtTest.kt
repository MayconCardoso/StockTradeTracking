package com.mctech.stocktradetracking.data.stock_share.mapper

import com.mctech.stocktradetracking.data.stock_share.database.StockShareDatabaseEntity
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.*

class StockShareMapperKtTest {

    @Test
    fun `should map to database entity`(){
        val share = StockShare(
            id = 1,
            code = "MGLU3",
            shareAmount = 1000,
            purchasePrice = 45.89,
            salePrice = 50.91,
            purchaseDate = Calendar.getInstance().time,
            saleDate = Calendar.getInstance().time,
            isPositionOpened = false
        )

        val target = share.toDatabaseEntity()

        Assertions.assertThat(share.id).isEqualTo(target.id)
        Assertions.assertThat(share.code).isEqualTo(target.code)
        Assertions.assertThat(share.shareAmount).isEqualTo(target.shareAmount)
        Assertions.assertThat(share.purchasePrice).isEqualTo(target.purchasePrice)
        Assertions.assertThat(share.purchaseDate).isEqualTo(target.purchaseDate)
        Assertions.assertThat(share.salePrice).isEqualTo(target.salePrice)
        Assertions.assertThat(share.saleDate).isEqualTo(target.saleDate)
        Assertions.assertThat(share.isPositionOpened).isEqualTo(target.isPositionOpened)
    }

    @Test
    fun `should map to business entity`(){
        val share = StockShareDatabaseEntity(
            id = 1,
            code = "MGLU3",
            shareAmount = 1000,
            purchasePrice = 45.89,
            salePrice = 50.91,
            purchaseDate = Calendar.getInstance().time,
            saleDate = Calendar.getInstance().time,
            isPositionOpened = false
        )

        val target = share.toBusinessEntity()

        Assertions.assertThat(share.id).isEqualTo(target.id)
        Assertions.assertThat(share.code).isEqualTo(target.code)
        Assertions.assertThat(share.shareAmount).isEqualTo(target.shareAmount)
        Assertions.assertThat(share.purchasePrice).isEqualTo(target.purchasePrice)
        Assertions.assertThat(share.purchaseDate).isEqualTo(target.purchaseDate)
        Assertions.assertThat(share.salePrice).isEqualTo(target.salePrice)
        Assertions.assertThat(share.saleDate).isEqualTo(target.saleDate)
        Assertions.assertThat(share.isPositionOpened).isEqualTo(target.isPositionOpened)
    }
}