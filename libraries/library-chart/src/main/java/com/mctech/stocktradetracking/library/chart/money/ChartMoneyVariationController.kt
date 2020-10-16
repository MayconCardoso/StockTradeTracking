package com.mctech.stocktradetracking.library.chart.money

import android.content.Context
import com.mctech.stocktradetracking.library.chart.dp
import com.mctech.stocktradetracking.library.chart.money.ChartMoneyVariationConstants.CIRCLE_RADIOS
import com.mctech.stocktradetracking.library.chart.pixel
import kotlin.math.max
import kotlin.math.min

internal class ChartMoneyVariationController(
  private val context: Context
) {
  // Data values
  private val data by lazy { mutableListOf<MoneyVariationEntry>() }
  internal val dataElements by lazy { mutableListOf<MoneyVariationView>() }
  private var minDataValue = 0.0
  private var maxDataValue = 0.0
  private val circleMarkerRadius by lazy { context.dp(CIRCLE_RADIOS) }
  private var viewHeight: Float = 0.0F
  private var viewWidth: Int = 0
  internal var centerZeroY = 0.0F
  internal var distanceBetweenElements = 0.0F

  internal fun setData(data: List<MoneyVariationEntry>) {
    this.data.clear()
    this.data.addAll(data)

    minDataValue = data.minBy { it.amount }?.amount ?: 0.0
    maxDataValue = data.maxBy { it.amount }?.amount ?: 0.0
  }

  internal fun updateItemsDimensions(width: Int, height: Int) {
    // Cache view dimensions.
    viewHeight = height.toFloat()
    viewWidth = width

    // The size of each column of the chart.
    distanceBetweenElements = (width - circleMarkerRadius) / data.size

    // Calculate the center of chart.
    centerZeroY = computeCenterZeroY()

    // Reset control
    dataElements.clear()

    // Calculate the position of each element
    data.forEach { data ->
      dataElements.add(
        MoneyVariationView(
          data = data,
          y = computeElementYPosition(data.amount)
        )
      )
    }
  }

  private fun circleSizeOffset() = context.pixel(circleMarkerRadius) * 2

  private fun computeElementYPosition(dataValue: Double): Float {
    // Compute position related to view size and center Y
    val relativePosition = ((dataValue * viewHeight / computeRange()) * -1).toFloat()

    // Return computed value.
    val y = centerZeroY + relativePosition

    // Internal offset padding
    val offset = circleSizeOffset() * 2

    // Apply margin
    return when {
      y < centerZeroY -> max(y, offset)
      y > centerZeroY -> min(viewHeight - offset, y)
      else -> y
    }
  }

  private fun computeRange(): Double {
    return maxDataValue - minDataValue
  }

  private fun computeCenterZeroY(): Float {
    // There are only positive values
    if (maxDataValue >= 0 && minDataValue >= 0) {
      return viewHeight
    }

    // There are only negative values
    if (maxDataValue < 0 && minDataValue < 0) {
      return 0F + circleSizeOffset()
    }

    // There are both values, so I need to find the center based on values
    return (maxDataValue * viewHeight / computeRange()).toFloat() + circleSizeOffset()
  }
}

