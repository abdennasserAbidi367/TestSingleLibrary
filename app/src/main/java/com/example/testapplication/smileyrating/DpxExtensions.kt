package com.example.testapplication.smileyrating

import android.content.res.Resources

fun Float.toDp(): Float {
    return this * Resources.getSystem().displayMetrics.density
}

fun Float.toPx(): Float {
    return this / Resources.getSystem().displayMetrics.density
}

fun Int.toDp(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun Int.toPx(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}