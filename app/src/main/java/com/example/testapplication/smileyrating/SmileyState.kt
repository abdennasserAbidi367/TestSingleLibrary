package com.example.testapplication.smileyrating

internal sealed class SmileyState {
    object Sad : SmileyState()
    object Neutral : SmileyState()
    object Okay : SmileyState()
    object Happy : SmileyState()
    object Amazing : SmileyState()

    companion object {
        fun of(value: Int): SmileyState {
            return when (value) {
                1 -> Sad
                2 -> Neutral
                3 -> Okay
                4 -> Happy
                5 -> Amazing
                else -> Neutral
            }
        }
    }
}