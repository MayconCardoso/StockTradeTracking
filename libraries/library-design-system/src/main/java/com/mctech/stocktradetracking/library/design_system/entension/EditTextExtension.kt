package com.mctech.stocktradetracking.library.design_system.entension

import android.widget.EditText

fun EditText.getDoubleValue(): Double {
  return text.toString().takeIf { it.isNotBlank() }?.toDouble() ?: 0.0
}

fun EditText.getLongValue(): Long {
  return text.toString().takeIf { it.isNotBlank() }?.toLong() ?: 0
}