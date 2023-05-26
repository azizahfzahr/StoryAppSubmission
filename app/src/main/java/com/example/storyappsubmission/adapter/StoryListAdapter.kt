package com.example.storyappsubmission.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.storyappsubmission.databinding.ItemStoryBinding
import com.example.storyappsubmission.detail.DetailStoryActivity
import com.example.storyappsubmission.response.story

class StoryListAdapter : PagingDataAdapter<story, StoryListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener{
                val intent = Intent(holder.itemView.context, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.NAME,data.name)
                intent.putExtra(DetailStoryActivity.DESC,data.description)
                intent.putExtra(DetailStoryActivity.URL,data.photoUrl)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: story) {
            binding.apply {
                Glide.with((itemView))
                    .load(data.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(rvStory)
                nama.text = data.name
            }
        }
    }

    companion object {
         val DIFF_CALLBACK = object : DiffUtil.ItemCallback<story>() {
            override fun areItemsTheSame(oldItem: story, newItem: story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: story, newItem: story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}