package com.mctech.stocktradetracking.library.chart.money

import android.content.Context
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.mctech.stocktradetracking.library.chart.R
import com.mctech.stocktradetracking.library.chart.dp

internal class ChartMoneyVariationPainter(
  private val context: Context,
  private val callback: ChartMoneyVariationPainterCallback
) {
  // View aspect size
  private val variationLineSize by lazy { context.dp(2) }

  // Painters
  internal val positivePaint by lazy { defaultLinePaint(positiveColor) }
  internal val negativePaint by lazy { defaultLinePaint(negativeColor) }
  internal val centerPainter by lazy {
    defaultLinePaint(R.color.colorCenterChartLine).apply {
      strokeWidth = context.dp(1)
    }
  }

  private var positiveColor: Int = R.color.colorPositiveValue
    set(value) {
      field = value
      updatePaintColor(positivePaint, value)
    }
  private var negativeColor: Int = R.color.colorNegativeValue
    set(value) {
      field = value
      updatePaintColor(negativePaint, value)
    }

  private fun defaultLinePaint(colorInt: Int) = Paint().apply {
    isAntiAlias = true
    style = Paint.Style.FILL
    strokeWidth = variationLineSize
    color = ContextCompat.getColor(context, colorInt)
  }

  private fun updatePaintColor(paint: Paint, colorInt: Int) {
    paint.color = ContextCompat.getColor(context, colorInt)
    callback.invalidate()
  }
}

interface ChartMoneyVariationPainterCallback {
  fun invalidate()
}

