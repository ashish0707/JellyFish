package com.spotify.voice.experience.view.listening

import java.lang.Math.pow
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class CircleMath {
    companion object {
        fun degrees2rad(number: Float): Float = (number * Math.PI / 180f).toFloat()

        /**
         **gfn**
        Need to figure out where this lives in math.
        Takes a value, X.
        Raises x to the power of 4 and adds 8 to it.
        Takes the result, and puts it under 8.
        Takes the result, and raises it to the power of 2. <- this power helps define the decay curve length.
        Returns a fraction.
         */
//((8 + (8/(x^8))^2)
        fun gfn(x: Float): Float {
//            return (8f + (8.0 / x.toDouble().pow(8.0)).pow( 2.0)).toFloat()
//            pow((8 / (8 + pow(x, 8))), 2)
            return pow((8 / (8 + pow(x.toDouble(), 8.0))), 2.0).toFloat()
        }


        /**
         **Path Adjustment**
        Takes the attentuation, frequency and phase of a curve.
        Solves for X
        This is used to create the sin wave on a circle.
         */

        fun pathAdjustment(attenuation: Float, frequency: Float, phase: Float): (x: Float) -> Float =
            { x -> gfn(x) * sin(frequency * x - phase) / attenuation }

        /**
         **Get X**
        Given the center of a circle, it's radius, and the current degree, will return the X value for the point on the circle's circumfrance.
         */

        fun getX(centerX: Float, radius: Float, degree: Float): Float =
            centerX + radius * cos(degrees2rad(degree))

        /**
         **Get Y**
        Given the center of a circle, it's radius, and the current degree, will return the Y value for the point on the circle's circumfrance.
         */
        fun getY(centerY: Float, radius: Float, degree: Float): Float =
            centerY + radius * sin(degrees2rad(degree))

        /**
         **Warped Radius**
        Given some values, we'll calculate a new radius we can use to warp our circle with a sine wave.
         */

        fun warpedRadius(
            degree: Float,
            centerX: Float,
            radius: Float,
            attenuation: Float,
            frequency: Float,
            phase: Float,
            amplitude: Float
        ): Float {
            val f = pathAdjustment(attenuation = attenuation, frequency = frequency, phase = phase)
            val x = (degree - centerX) / radius
            return f(x) * radius * amplitude + radius
        }

        /**
         **Relative sound level**
        Given a rating in decibles measured from -120 to 0 where 0 is the loudest., we'll return a number between 0 and 1 where 1 is the loudest.
         */

        fun relativeSoundLevel(decibles: Float): Float {
            return 10.0f.pow(decibles / 20.0f)
        }
    }
}
