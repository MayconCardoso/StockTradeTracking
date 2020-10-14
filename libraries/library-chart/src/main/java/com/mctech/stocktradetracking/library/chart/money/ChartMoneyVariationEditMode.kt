package com.mctech.stocktradetracking.library.chart.money


object ChartMoneyVariationEditMode {

  fun attach(chart: ChartMoneyVariationView) {
    if (!chart.isInEditMode) {
      return
    }

    chart.setData(
      arrayListOf(
        MoneyVariationEntry(0.0),
        MoneyVariationEntry(315.46),
        MoneyVariationEntry(2576.76),
        MoneyVariationEntry(2520.01),
        MoneyVariationEntry(10000.00),
        MoneyVariationEntry(-44951.10),
        MoneyVariationEntry(-28201.62),
        MoneyVariationEntry(-18526.86),
        MoneyVariationEntry(12100.0),
        MoneyVariationEntry(16796.76)
      )
    )
  }
}

