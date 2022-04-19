package com.example.testapplication.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import com.example.testapplication.R
import com.example.testapplication.smileyrating.SmileyRatingView

class SecondFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_second, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar)
        val smileyView = view.findViewById<SmileyRatingView>(R.id.smiley_view)
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            smileyView.setSmiley(rating = rating)
        }
    }

    companion object {
        fun newInstance(): SecondFragment = SecondFragment()
    }
}