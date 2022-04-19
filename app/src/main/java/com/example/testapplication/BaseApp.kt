package com.example.testapplication

import android.app.Application
import com.google.android.libraries.places.api.Places
import java.util.*

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (!Places.isInitialized()) {
            Places.initialize(
                applicationContext,
                "AIzaSyC36KnKbMwtI3FTwneSXjJpJPW6nc6kqpk",
                Locale.US
            )
        }
    }
}