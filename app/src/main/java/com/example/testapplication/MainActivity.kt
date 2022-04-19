package com.example.testapplication

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.testapplication.blurkit.BlurLayout

class MainActivity : AppCompatActivity() {

    lateinit var blurLayout: BlurLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView = findViewById<ImageView>(R.id.tImage)
        blurLayout = findViewById(R.id.blurLayout)
       /* Glide
            .with(this)
            .load("https://m.media-amazon.com/images/M/MV5BNGZiMzBkZjMtNjE3Mi00MWNlLWIyYjItYTk3MjY0Yjg5ODZkXkEyXkFqcGdeQXVyNDg4NjY5OTQ@._V1_SX300.jpg")
            .into(imageView)*/

    }

    override fun onStart() {
        super.onStart()
        blurLayout.startBlur()
    }

    override fun onStop() {
        super.onStop()
        blurLayout.pauseBlur()
    }
}