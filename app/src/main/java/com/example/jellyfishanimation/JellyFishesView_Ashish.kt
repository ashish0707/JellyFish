package com.example.jellyfishanimation

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.spotify.voice.experience.view.listening.JellyFishView_Ashish
import kotlin.random.Random

//class JellyFishesView_Ashish : FrameLayout {

//    enum class state {
//        noState,
//        transitionToListening,
//        listening,
//        transitionToThinking,
//        thinking,
//        transitionToNone
//    }
//
//    private val BLUE_ATTENUATION = -100.0
//    private val NEON_ATTENUATION = -1.8
//    private val TEAL_ATTENUATION = -1.4
//    private var currentState = state.noState
//    private var currentAmplitude: Double = 0.0
//    private var listeningMaxAmplitude: Double = 0.3
//    private var listeningMinAmplitude: Double = 0.1
//    private var thinkingAmplitude: Double = 0.2
//    private var thinkingFrequency: Double = 6.0
//    private var frequency = 3.0
//    private var phase = 1.0
//    private var speed = 0.03
//
//
//    private lateinit var jellyFishNeonAshish: JellyFishView_Ashish
//    private lateinit var jellyFishTealAshish: JellyFishView_Ashish
//    private lateinit var jellyFishBlueAshish: JellyFishView_Ashish
//
//    constructor(context: Context) : super(context)
//    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
//        context,
//        attrs,
//        defStyle
//    )
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
//
//    init {
//        initialize()
//    }
//
//    private fun initialize() {
//        val view = LayoutInflater.from(context).inflate(R.layout.view_jellyfish, this, true)
//
//        jellyFishNeonAshish = view.findViewById(R.id.jellyFishNeon)
//        jellyFishTealAshish = view.findViewById(R.id.jellyFishTeal)
//        jellyFishBlueAshish = view.findViewById(R.id.jellyFishBlue)
//
//        jellyFishBlueAshish.attenuation = BLUE_ATTENUATION
//        jellyFishBlueAshish.lineWidth = 4
//        jellyFishBlueAshish.color = R.color.jellyfish_blue
//
//        jellyFishNeonAshish.attenuation = NEON_ATTENUATION
//        jellyFishNeonAshish.lineWidth = 8
//        jellyFishNeonAshish.color = R.color.jellyfish_neon
//
//        jellyFishTealAshish.attenuation = TEAL_ATTENUATION
//        jellyFishTealAshish.lineWidth = 8
//        jellyFishTealAshish.color = R.color.jellyfish_teal
//    }
//
//    fun startListening() {
//        frequency = 2.0
//        currentState = state.transitionToListening
//        invalidate()
//    }
//
//    fun startThinking() {
//        currentState = state.transitionToThinking
//    }
//
//    fun stopListening() {
//        currentState = state.transitionToNone
//    }
//
//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//        warpCircles()
//    }
//
//    private fun warpCircles() {
//        phase = (phase + Math.PI * speed).rem(2 * Math.PI)
//        if (currentState == state.transitionToListening) {
//            currentAmplitude += 0.01
//            if (currentAmplitude >= listeningMaxAmplitude) {
//                currentAmplitude = listeningMaxAmplitude
//                currentState = state.listening
//            }
//        } else if (currentState == state.transitionToNone) {
//            currentAmplitude -= 0.01
//            if (currentAmplitude <= 0.001) {
//                currentAmplitude = 0.0
//                currentState = state.noState
//                invalidate()
//            }
//        } else if (currentState == state.transitionToThinking && currentAmplitude < thinkingAmplitude) {
//            currentAmplitude += 0.02
//            if (frequency <= thinkingFrequency) {
//                frequency += 0.2
//            }
//            if (currentAmplitude == thinkingAmplitude) {
//                currentState = state.thinking
//            }
//        } else if (currentState == state.transitionToThinking && currentAmplitude > thinkingAmplitude) {
//            currentAmplitude -= 0.02
//            if (frequency <= thinkingFrequency) {
//                frequency += 0.2
//            }
//            if (currentAmplitude == thinkingAmplitude) {
//                currentState = state.thinking
//            }
//        }
//        if (currentState == state.listening) {
////            currentAmplitude = CircleMath.relativeSoundLevel(Random.nextDouble(0.0, 1.0))
//            currentAmplitude = Random.nextDouble(0.0, 1.0)
////            Log.d("some", "Current amplitude is : $currentAmplitude")
//            frequency = 2.0
//            if (currentAmplitude >= 0.5) {
//                currentAmplitude = 0.5
//            }
//            if (currentAmplitude <= 0.1) {
//                currentAmplitude = 0.1
//            }
//            if (frequency >= 20) {
//                frequency = 20.0
//            }
//        }
//
//        updatePhase()
//        updateFrequency()
//        updateAmplitude()
//        jellyFishTealAshish.invalidate()
//        jellyFishNeonAshish.invalidate()
//        jellyFishBlueAshish.invalidate()
//    }
//
//    private fun updateAmplitude() {
//        jellyFishNeonAshish.amplitude = currentAmplitude
//        jellyFishTealAshish.amplitude = currentAmplitude
//        jellyFishBlueAshish.amplitude = currentAmplitude
//    }
//
//    private fun updateFrequency() {
//        jellyFishNeonAshish.frequency = frequency
//        jellyFishTealAshish.frequency = frequency
//        jellyFishBlueAshish.frequency = frequency
//    }
//
//    private fun updatePhase() {
//        jellyFishBlueAshish.phase = phase
//        jellyFishNeonAshish.phase = phase
//        jellyFishTealAshish.phase = phase
//    }
//
//    fun update(amplitude: Double) {
//        warpCircles()
//    }
//}
