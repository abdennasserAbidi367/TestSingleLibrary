package com.example.testapplication.signup

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapplication.R

class PostMediaAdapter(
    private var listMedia: MutableList<Post> = mutableListOf(),
    private val interactor: Interactor? = null
) : RecyclerView.Adapter<PostMediaAdapter.PostMediaViewHolder>() {

    inner class PostMediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Post, position: Int) {
            val fabRemove = itemView.findViewById<ImageView>(R.id.fab_remove_media)
            if (item.uri is Uri) {
                (item.uri as Uri?)?.let {
                    Glide.with(itemView)
                        .load(it)
                        .into(itemView.findViewById(R.id.imageView))
                }
            } else if (item.uri is Bitmap) {
                (item.uri as Bitmap?)?.let {
                    itemView.findViewById<ImageView>(R.id.imageView).setImageBitmap(it)
                }
            }
            fabRemove.visibility = if (item.uri != null) View.VISIBLE
            else View.GONE

            fabRemove.setOnClickListener {
                interactor?.onRemove(item, position)
            }

            val fab = itemView.findViewById<ImageView>(R.id.fab_add_media)
            fab.setOnClickListener {
                interactor?.onClick(item, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostMediaViewHolder =
        PostMediaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false)
        )

    override fun onBindViewHolder(holder: PostMediaViewHolder, position: Int) {
        holder.bindView(listMedia[position], position)
    }

    override fun getItemCount(): Int  = listMedia.size

    fun addList(listMedia: MutableList<Post>, index: Int) {
        /*val callback = PostDiffUtils(this.listMedia, listMedia)
        val result = DiffUtil.calculateDiff(callback)
        result.dispatchUpdatesTo(this)*/
        this.listMedia = listMedia
        notifyItemChanged(index)
    }
}
