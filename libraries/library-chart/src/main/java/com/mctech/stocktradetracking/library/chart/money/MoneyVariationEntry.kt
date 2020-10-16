package com.mctech.stocktradetracking.library.chart.money

data class MoneyVariationEntry(
  val amount: Double
)

internal data class MoneyVariationView(
  val data: MoneyVariationEntry,
  var y: Float
)