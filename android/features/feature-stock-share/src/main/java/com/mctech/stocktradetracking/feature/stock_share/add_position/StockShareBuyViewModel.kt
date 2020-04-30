package com.mctech.stocktradetracking.feature.stock_share.add_position

import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.stocktradetracking.domain.stock_share.entity.StockShare
import com.mctech.stocktradetracking.domain.stock_share.interaction.SaveStockShareCase
import com.mctech.stocktradetracking.feature.stock_share.StockShareCommand
import com.mctech.stocktradetracking.feature.stock_share.StockShareInteraction
import java.util.*

class StockShareBuyViewModel constructor(
    private val saveStockShareCase: SaveStockShareCase
) : BaseViewModel() {

    override suspend fun handleUserInteraction(interaction: UserInteraction) {
        when (interaction) {
            is StockShareInteraction.AddPosition -> addStockPositionInteraction(
                interaction.code,
                interaction.amount,
                interaction.price
            )
        }
    }

    private suspend fun addStockPositionInteraction(code: String, amount: Long, price: Double) {
        // Save new position
        saveStockShareCase.execute(
            StockShare(
                code = code.toUpperCase(Locale.getDefault()),
                shareAmount = amount,
                purchasePrice = price,
                purchaseDate = Calendar.getInstance().time
            )
        )

        // Send command to get back
        sendCommand(StockShareCommand.Back.FromBuy)
    }
}
