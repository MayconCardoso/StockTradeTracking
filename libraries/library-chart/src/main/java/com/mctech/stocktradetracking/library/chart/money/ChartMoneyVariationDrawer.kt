package com.mctech.stocktradetracking.library.chart.money

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import com.mctech.stocktradetracking.library.chart.dp
import com.mctech.stocktradetracking.library.chart.utils.computeCenterProjectionRatio
import kotlin.math.acos
import kotlin.math.asin

internal class ChartMoneyVariationDrawer(
  private val context: Context
) {
  /**
   * Computed value that represents the position of R$0.00 on the chart.
   * It is used to defined the path direction and also the correct painter that will be used to draw the line and the circle marker.
   */
  private var centerZeroY = 0.0F

  /**
   * Computed value that represents the distance between each element (Element width)
   */
  private var elementSize = 0.0F

  /**
   * Current relative X position on the chart. Used while drawing elements.
   */
  private var currentXPosition = 0.0F

  /**
   * Current relative Y position on the chart. Used while drawing elements.
   */
  private var currentYPosition = 0.0F

  /**
   * Resolved data collection with drawn dimensions like height and width
   */
  private val dataElements = mutableListOf<MoneyVariationView>()

  /**
   * The size in DP of circle radius used draw a marker in the end of each element.
   */
  private val circleMarkerRadius by lazy { context.dp(ChartMoneyVariationConstants.CIRCLE_RADIOS) }

  internal fun prepare(
    centerZeroY: Float,
    elementSize: Float,
    data: List<MoneyVariationView>
  ) {
    this.centerZeroY = centerZeroY
    this.elementSize = elementSize
    this.dataElements.clear()
    this.dataElements.addAll(data)
  }

  internal fun draw(canvas: Canvas, painter: ChartMoneyVariationPainter) {
    canvas.drawCenterLine(painter)
    canvas.drawVariationLines(painter)
    canvas.drawCircleIndicators(painter)
  }

  /**
   * Iterator that control and update the current position on screen.
   */
  private fun drawData(draw: (data: MoneyVariationView) -> Unit) {
    currentYPosition = centerZeroY
    currentXPosition = 0.0F

    dataElements.forEach { data ->
      draw(data)
      currentXPosition += elementSize
      currentYPosition = data.y
    }
  }

  private fun Canvas.drawVariationLines(painter: ChartMoneyVariationPainter) = drawData { element ->
    when {
      // It was on profit path and will continue on it.
      element.isOnProfitPath() -> {
        drawMoneyVariationLine(painter.positivePaint, element.y)
      }

      // It was on profit path but now it will be change to loss line.
      element.hasChangedFromLossToProfit() -> {
        drawMoneyVariationLineWhenHitZero(
          firstPaint = painter.negativePaint,
          secondPaint = painter.positivePaint,
          finalY = element.y,
          centerX = computeProfitCenterX(element.y)
        )
      }

      // It was on loss path but now it will be change to profit line.
      element.hasChangedFromProfitToLoss() -> {
        drawMoneyVariationLineWhenHitZero(
          firstPaint = painter.positivePaint,
          secondPaint = painter.negativePaint,
          finalY = element.y,
          centerX = computeLossCenterX(element.y)
        )
      }

      // It was on loss path and will continue on it.
      element.isOnLossPath() -> {
        drawMoneyVariationLine(painter.negativePaint, element.y)
      }
    }
  }

  private fun Canvas.drawCircleIndicators(painter: ChartMoneyVariationPainter) =
    drawData { element ->
      if (element.data.amount >= 0) {
        drawCircleIndicator(painter.positivePaint, element.y)
      } else {
        drawCircleIndicator(painter.negativePaint, element.y)
      }
    }

  private fun Canvas.drawMoneyVariationLine(paint: Paint, finalY: Float) {
    drawLine(
      currentXPosition,
      currentYPosition,
      currentXPosition + elementSize,
      finalY,
      paint
    )
  }

  private fun Canvas.drawMoneyVariationLineWhenHitZero(
    firstPaint: Paint,
    secondPaint: Paint,
    centerX: Float,
    finalY: Float
  ) {
    drawLine(
      currentXPosition,
      currentYPosition,
      centerX,
      centerZeroY,
      firstPaint
    )

    drawLine(
      centerX,
      centerZeroY,
      currentXPosition + elementSize,
      finalY,
      secondPaint
    )
  }

  private fun Canvas.drawCircleIndicator(paint: Paint, finalY: Float) {
    drawCircle(
      currentXPosition + elementSize,
      finalY,
      circleMarkerRadius,
      paint
    )
  }

  private fun Canvas.drawCenterLine(painter: ChartMoneyVariationPainter) {
    drawLine(
      0.toFloat(),
      centerZeroY,
      width.toFloat(),
      centerZeroY,
      painter.centerPainter
    )
  }

  private fun computeLossCenterX(finalY: Float) = computeCenterProjectionRatio(
    pointA = PointF(currentXPosition, currentYPosition),
    pointB = PointF(currentXPosition + elementSize, finalY),
    pointC = PointF(currentXPosition, finalY),
    intersectionHeight = centerZeroY - currentYPosition,
    currentXPosition = currentXPosition,
    angleFunction = { value ->
      acos(value)
    }
  )

  private fun computeProfitCenterX(finalY: Float) = computeCenterProjectionRatio(
    pointA = PointF(currentXPosition, currentYPosition),
    pointB = PointF(currentXPosition + elementSize, finalY),
    pointC = PointF(currentXPosition + elementSize, currentYPosition),
    intersectionHeight = currentYPosition - centerZeroY,
    currentXPosition = currentXPosition,
    angleFunction = { value ->
      asin(value)
    }
  )

  private fun MoneyVariationView.isOnProfitPath(): Boolean {
    return y <= centerZeroY && currentYPosition <= centerZeroY
  }

  private fun MoneyVariationView.isOnLossPath(): Boolean {
    return y > centerZeroY && currentYPosition > centerZeroY
  }

  private fun MoneyVariationView.hasChangedFromLossToProfit(): Boolean {
    return y <= centerZeroY && currentYPosition > centerZeroY
  }

  private fun MoneyVariationView.hasChangedFromProfitToLoss(): Boolean {
    return y > centerZeroY && currentYPosition <= centerZeroY
  }
}

