package com.example.storyappsubmission.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.storyappsubmission.databinding.ItemStoryBinding
import com.example.storyappsubmission.response.story

class Adaptor : RecyclerView.Adapter<Adaptor.UserViewHolder>() {
    private val list = ArrayList<story>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(users: ArrayList<story>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user : story) {
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with((itemView))
                    .load(user.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(rvStory)
                nama.text = user.name
            }
        }
    }

    override fun onCreateViewHolder(qwe: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemStoryBinding.inflate(LayoutInflater.from(qwe.context),qwe,false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(hold: UserViewHolder, position: Int) {
        hold.bind(list[position])

    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: story)
    }
}