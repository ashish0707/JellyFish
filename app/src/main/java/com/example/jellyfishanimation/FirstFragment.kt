package com.example.jellyfishanimation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.jellyfishanimation.databinding.FragmentFirstBinding
import com.spotify.voice.experience.view.listening.CircleMath
import com.spotify.voice.experience.view.listening.CircleMath.Companion.degrees2rad
import java.util.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newRadius = CircleMath.warpedRadius(
            0.0f, 207.0f, 68.0f, -1.80f, 2.0f, 2.0996091f, 0.34677434f)
        Log.d("circle" , "Value is $newRadius"
        )
        val xCoordinate = 207.0f + newRadius * cos(degrees2rad(0.0f - 2120f))
        val yCoordinate = 458.5f + newRadius * sin(degrees2rad(0.0f - 2120f))
        Log.d("circle" , "xCoordinate is $xCoordinate")
        Log.d("circle" , "yCoordinate is $yCoordinate")
        // expected 68.000015 259.0910339355469 502.2095642089844
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}