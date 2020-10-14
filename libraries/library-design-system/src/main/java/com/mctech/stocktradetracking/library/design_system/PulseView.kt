package com.mctech.stocktradetracking.library.design_system

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat

class PulseView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  private val view = LayoutInflater.from(context).inflate(
    R.layout.view_pulse_indicator, null, false
  )

  private val imgFirstPulse = view.findViewById<AppCompatImageView>(R.id.imgFirstPulse)
  private val imgSecondPulse = view.findViewById<AppCompatImageView>(R.id.imgSecondPulse)
  private val staticImg = view.findViewById<AppCompatImageView>(R.id.staticImg)
  private val handlerAnimation = Handler()

  private val animationRunnable: Runnable by lazy {
    Runnable {
      imgFirstPulse.animate().scaleX(1.8F).scaleY(1.8F).alpha(0F).setDuration(2000)
        .withEndAction {
          imgFirstPulse.scaleX = 0.7F
          imgFirstPulse.scaleY = 0.7F
          imgFirstPulse.alpha = 1F

          handlerAnimation.post(this.animationRunnable)
        }

      staticImg.animate().scaleX(0.1F).scaleY(0.1F).setDuration(500)
        .withEndAction {
          staticImg.scaleX = 1F
          staticImg.scaleY = 1F
        }

      imgSecondPulse.animate().scaleX(1.8F).scaleY(1.8F).alpha(0F).setDuration(1400)
        .withEndAction {
          imgSecondPulse.scaleX = 0.7F
          imgSecondPulse.scaleY = 0.7F
          imgSecondPulse.alpha = 1F
        }
    }
  }

  init {
    addView(view)
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    startPulse()
  }

  fun startPulse() {
    stopPulse()
    animationRunnable.run()
  }

  fun stopPulse() {
    handlerAnimation.removeCallbacks(animationRunnable)
  }

  fun changeViewColor(color: Int) {
    val tintColor = ContextCompat.getColor(context, color)

    staticImg.setColorFilter(tintColor, android.graphics.PorterDuff.Mode.SRC_IN)
    imgFirstPulse.setColorFilter(tintColor, android.graphics.PorterDuff.Mode.SRC_IN)
    imgSecondPulse.setColorFilter(tintColor, android.graphics.PorterDuff.Mode.SRC_IN)
  }
}