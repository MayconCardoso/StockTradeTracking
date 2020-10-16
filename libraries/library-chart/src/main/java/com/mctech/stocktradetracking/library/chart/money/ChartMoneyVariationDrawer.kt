package com.mctech.stocktradetracking.library.chart.money

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import com.mctech.stocktradetracking.library.chart.dp
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.sin
import kotlin.math.sqrt

internal class ChartMoneyVariationDrawer(
  private val context: Context
) {
  private var currentXPosition = 0.0F
  private var currentYPosition = 0.0F
  private var centerZeroY = 0.0F
  private var elementSize = 0.0F
  private val dataElements = mutableListOf<MoneyVariationView>()
  private val circleMarkerRadius by lazy {
    context.dp(ChartMoneyVariationConstants.CIRCLE_RADIOS)
  }

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
      element.isOnProfitPath() -> {
        drawMoneyVariationLine(painter.positivePaint, element.y)
      }
      element.hasChangedFromProfitToLoss() -> {
        drawMoneyVariationLine(painter.positivePaint, element.y)
        //drawMoneyVariationLineWhenHitZero(painter.negativePaint, painter.positivePaint, element.y)
      }
      element.hasChangedFromLossToProfit() -> {
        drawMoneyVariationLine(painter.negativePaint, element.y)
        //drawMoneyVariationLineWhenHitZero(painter.positivePaint, painter.negativePaint, element.y)
      }
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
    finalY: Float
  ) {
    var y = abs(currentYPosition - finalY)

    // Get points of triangle
    val pointA = PointF(0F, currentYPosition)
    val pointB = PointF(elementSize, y)
    val pointC = PointF(0F, y)

    // Calculate distance between points.
    val a2 = lengthSquare(pointB, pointC)
    val b2 = lengthSquare(pointA, pointC)
    val c2 = lengthSquare(pointA, pointB)

    // length of sides be a, b, c
    val a = sqrt(a2)
    val b = sqrt(b2)
    val c = sqrt(c2)

    // From Cosine law
    var alpha = acos((b2 + c2 - a2) / (2 * b * c))
    var betta = acos((a2 + c2 - b2) / (2 * a * c))

    // Converting to degree
    alpha = (alpha * 180F / PI).toFloat()
    betta = (betta * 180F / PI).toFloat()

    y = finalY - centerZeroY
    val hype = y / sin(alpha)

    val centerHitX = sqrt(hype * hype - y * y)

    drawLine(
      currentXPosition,
      currentYPosition,
      currentXPosition + centerHitX,
      centerZeroY,
      firstPaint
    )

    drawLine(
      currentXPosition + centerHitX,
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

  private fun lengthSquare(p1: PointF, p2: PointF): Float {
    val xDiff: Float = p1.x - p2.x
    val yDiff: Float = p1.y - p2.y
    return xDiff * xDiff + yDiff * yDiff
  }

  private fun MoneyVariationView.isOnProfitPath(): Boolean {
    return y <= centerZeroY && currentYPosition <= centerZeroY
  }

  private fun MoneyVariationView.isOnLossPath(): Boolean {
    return y > centerZeroY && currentYPosition > centerZeroY
  }

  private fun MoneyVariationView.hasChangedFromProfitToLoss(): Boolean {
    return y <= centerZeroY && currentYPosition > centerZeroY
  }

  private fun MoneyVariationView.hasChangedFromLossToProfit(): Boolean {
    return y > centerZeroY && currentYPosition <= centerZeroY
  }
}

