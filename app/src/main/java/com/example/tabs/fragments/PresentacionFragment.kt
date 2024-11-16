package com.example.tabs.fragments

import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tabs.R
import androidx.fragment.app.Fragment

class PresentacionFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_presentacion, container, false)

        val titulo: TextView = rootView.findViewById(R.id.titulo)

        val fadeIn = ObjectAnimator.ofFloat(titulo, "alpha", 0f, 1f)
        fadeIn.duration = 1000

        val translation = ObjectAnimator.ofFloat(titulo, "translationY", -500f, 0f)
        translation.duration = 1000

        val scaleX = ObjectAnimator.ofFloat(titulo, "scaleX", 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(titulo, "scaleY", 0f, 1f)
        val scaleSet = AnimatorSet()
        scaleSet.playTogether(scaleX, scaleY)
        scaleSet.duration = 1000

        val rotation = ObjectAnimator.ofFloat(titulo, "rotation", 0f, 360f)
        rotation.duration = 1000

        val color = ObjectAnimator.ofArgb(titulo, "textColor", Color.GREEN, Color.GREEN)
        color.duration = 1000

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(fadeIn, translation, scaleSet, rotation, color)
        animatorSet.duration = 1000
        animatorSet.start()

        return rootView
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PresentacionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
