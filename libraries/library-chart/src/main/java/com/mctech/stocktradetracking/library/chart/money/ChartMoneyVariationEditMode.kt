package com.mctech.stocktradetracking.library.chart.money


internal class ChartMoneyVariationEditMode {

  internal fun attach(chart: ChartMoneyVariationView) {
    if (!chart.isInEditMode) {
      return
    }

    chart.setData(
      arrayListOf(
        MoneyVariationEntry(0.0),
        MoneyVariationEntry(0.0),
        MoneyVariationEntry(0.0),
        MoneyVariationEntry(315.46),
        MoneyVariationEntry(2276.76),
        MoneyVariationEntry(-23.01),
        MoneyVariationEntry(-18526.86),
        MoneyVariationEntry(-33526.86),
        MoneyVariationEntry(16100.0),
        MoneyVariationEntry(9000.76),
        MoneyVariationEntry(18000.76),
        MoneyVariationEntry(23000.76),
        MoneyVariationEntry(11000.76),
        MoneyVariationEntry(-10000.76),
        MoneyVariationEntry(26000.76)
      )
    )
  }
}

