package com.mctech.stocktradetracking.domain.extentions

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

fun Double.formatBrazilianCurrency(): String {
  return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this).replace(" ", "")
}

fun Double.round(decimals: Int = 2): Double {
  return BigDecimal(this).setScale(decimals, RoundingMode.HALF_EVEN).toDouble()
}

fun Double.toPercent(): String {
  return "${this}%"
}