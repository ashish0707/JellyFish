package com.example.jellyfishanimation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout

class JellyFishesView : FrameLayout {
    companion object {
        private const val BLUE_ATTENUATION = -100.0
        private const val NEON_ATTENUATION = -1.8
        private const val TEAL_ATTENUATION = -1.4
        private const val WIDE_STROKE = 8
        private const val OFFSET = -100.0
        private const val UPDATE_INTERVAL = 75L
        private const val MIN_LISTENING_AMPLITUDE = .1
        private const val MAX_LISTENING_AMPLITUDE = .2
    }

    enum class CircleColor(val id: Int) {
        BLUE(R.color.jellyfish_blue),
        NEON(R.color.jellyfish_neon),
        TEAL(R.color.jellyfish_teal)
    }

    private lateinit var jellyFishNeon: JellyFishView
    private lateinit var jellyFishTeal: JellyFishView
    private lateinit var jellyFishBlue: JellyFishView
    private lateinit var jellyHandler: Handler

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        initialize()
    }

    private fun initialize() {
        val view = LayoutInflater.from(context).inflate(R.layout.view_jellyfish, this, false)

//        jellyFishNeon = view.findViewById(R.id.jellyFishNeon)
//        jellyFishTeal = view.findViewById(R.id.jellyFishGreen)
//        jellyFishBlue = view.findViewById(R.id.jellyFishBlue)
//
//        jellyFishNeon.colorCode = CircleColor.NEON
//        jellyFishNeon.attenuation = NEON_ATTENUATION
//        jellyFishNeon.stroke = WIDE_STROKE
//        jellyFishNeon.setupPaint()
//
//        jellyFishTeal.colorCode = CircleColor.TEAL
//        jellyFishTeal.attenuation = TEAL_ATTENUATION
//        jellyFishTeal.stroke = 4
//        jellyFishTeal.offset = OFFSET
//        jellyFishTeal.setupPaint()
//
//        jellyFishBlue.colorCode = CircleColor.BLUE
//        jellyFishBlue.attenuation = BLUE_ATTENUATION
//        jellyFishTeal.stroke = 4
//        jellyFishBlue.setupPaint()
//
//        if (!::jellyHandler.isInitialized) {
//            val task = UpdateJellyFish(this)
//            jellyHandler = Handler(Looper.getMainLooper())
//            jellyHandler.postAtTime(task, UPDATE_INTERVAL)
//        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        jellyHandler.removeCallbacksAndMessages(null)
    }

    fun update(amplitude: Double) {
//        jellyFishNeon.amplitude = amplitude
//        jellyFishTeal.amplitude = amplitude
//        jellyFishBlue.amplitude = amplitude
//
//        jellyFishNeon.invalidate()
//        jellyFishTeal.invalidate()
//        jellyFishBlue.invalidate()
    }

    fun stopListeningAnimation() {
        jellyHandler.removeCallbacksAndMessages(null)
    }

    internal class UpdateJellyFish(val jellyFishesView: JellyFishesView) : Runnable {
        @SuppressLint("CallingMathRandom")
        override fun run() {
            jellyFishesView.update(Math.random().coerceIn(MIN_LISTENING_AMPLITUDE, MAX_LISTENING_AMPLITUDE))
        }
    }
}