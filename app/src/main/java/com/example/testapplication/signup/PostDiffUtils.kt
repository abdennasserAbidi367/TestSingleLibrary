package com.example.testapplication.signup

import androidx.recyclerview.widget.DiffUtil

class PostDiffUtils(private val oldList: List<Post>, private val newList: List<Post>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].uri == newList[newItemPosition].uri

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}