package com.projects.retrofitbasics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projects.retrofitbasics.databinding.ActivityItemBinding

class GitHubAdapter(
    private val list :List<GitHubRepo>
): RecyclerView.Adapter<GitHubAdapter.GitHubViewHolder>(){

    inner class GitHubViewHolder(val binding: ActivityItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubViewHolder {
        val binding = ActivityItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GitHubViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GitHubViewHolder, position: Int) {
        val currentItem = list[position]

        holder.binding.textView.text = currentItem.name
    }
}