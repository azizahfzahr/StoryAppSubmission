package com.example.storyappsubmission.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.storyappsubmission.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    companion object {
        const val NAME = "name"
        const val DESC = "deskripsi"
        const val URL = "url"
    }
    private lateinit var binding : ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(NAME)
        val deskripsi = intent.getStringExtra(DESC)
        val photo = intent.getStringExtra(URL)

        binding.description.text = deskripsi
        binding.userName.text = name
        Glide.with(this@DetailStoryActivity)
            .load(photo)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(binding.avaUser)

    }
}