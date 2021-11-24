package com.rishik.stockapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rishik.stockapp.adapters.NewsAdapter.*
import com.rishik.stockapp.databinding.NewsItemBinding
import com.rishik.stockapp.domain.News

class NewsClick(val block: (News) -> Unit) {
    /**
     * Called when a news is clicked
     *
     * @param news the news that was clicked
     */
    fun onClick(news: News) = block(news)
}

class NewsAdapter(private val callback: NewsClick) :
    ListAdapter<News, NewsViewHolder>(NewsViewHolder.NewsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, callback)
        }
    }

    class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News, callback: NewsClick) {
            binding.apply {
                this.news = news
                this.newsCallback = callback
            }
        }

        class NewsComparator : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News) =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem == newItem
        }
    }
}