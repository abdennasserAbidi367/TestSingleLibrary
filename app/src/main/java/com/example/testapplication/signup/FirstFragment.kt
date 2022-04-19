package com.example.testapplication.signup

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R

class FirstFragment : Fragment(), Interactor {

    private val list = mutableListOf(
        Post(0, null),
        Post(1, null),
        Post(2, null),
        Post(3, null),
        Post(4, null),
        Post(5, null),
        Post(6, null),
        Post(7, null),
        Post(8, null)
    )

    private var position = -1

    private val adapter by lazy { PostMediaAdapter(list, this) }

    private var activityInteractor: ActivityInteractor? = null

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted ->
            if (isGranted) content.launch("image/*")
            else Log.i("denied", "Permission is denied")
        }

    private val cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted ->
            if (isGranted) {
                val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val both = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
                val chooserIntent = Intent.createChooser(cameraIntent, "Capture Image or Video")
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(takeVideoIntent))

                takePic.launch(chooserIntent)
            } else Log.i("denied", "Permission is denied")
        }

    private val content =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            val index = if (position == -1) getFirstItemEmpty(list)
            else position
            list[index] = Post(index, uri)
            adapter.addList(list, index)
            position = -1
        }

    private val takePic =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val index = if (position == -1) getFirstItemEmpty(list)
                else position

                val intent = result.data
                if (intent?.extras == null) list[index] = Post(index, intent?.data)
                else list[index] = Post(index, intent.extras?.get("data") as Bitmap)

                adapter.addList(list, index)
                position = -1
            }
        }

    private fun getFirstItemEmpty(list: MutableList<Post>): Int {
        list.forEachIndexed { index, post ->
            if (post.uri == null) return index
        }
        return -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_first, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addLocation = view.findViewById<Button>(R.id.add_location)
        val rv = view.findViewById<RecyclerView>(R.id.postMedia)
        val addMedia = view.findViewById<Button>(R.id.add_media)
        val takePhoto = view.findViewById<Button>(R.id.add_media_video)
        rv.layoutManager = GridLayoutManager(context, 3)
        //adapter.addList(list)
        rv.adapter = adapter
        addMedia.setOnClickListener {
            activityResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        takePhoto.setOnClickListener {
            cameraResultLauncher.launch(Manifest.permission.CAMERA)
        }

        addLocation.setOnClickListener {
            activityInteractor?.onClick()
        }
    }

    companion object {
        fun newInstance(activityInteractor: ActivityInteractor): FirstFragment = FirstFragment().apply {
            this.activityInteractor = activityInteractor
        }
    }

    override fun onClick(post: Post, position: Int) {
        this.position = position
        content.launch("image/*")
    }

    override fun onRemove(post: Post, position: Int) {
        list[position] = Post(position, null)
        adapter.addList(list, position)
    }
}