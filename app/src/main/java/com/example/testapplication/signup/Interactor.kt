package com.example.testapplication.signup

interface Interactor {
    fun onClick(post: Post, position: Int)
    fun onRemove(post: Post, position: Int)
}