package com.rishik.stockapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rishik.stockapp.databinding.SearchItemBinding
import com.rishik.stockapp.domain.Stock

class SearchClick(val block: (Stock) -> Unit) {
    /**
     * Called when a stock is clicked
     */
    fun onClick(stock: Stock) = block(stock)
}

class SearchAdapter(private val callback: SearchClick) :
    ListAdapter<Stock, SearchAdapter.SearchViewHolder>(SearchViewHolder.SearchComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, callback)
        }
    }

    class SearchViewHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(stock: Stock, callback: SearchClick) {
            binding.apply {
                this.stock = stock
                this.searchCallback = callback
            }
        }

        class SearchComparator : DiffUtil.ItemCallback<Stock>() {
            override fun areItemsTheSame(oldItem: Stock, newItem: Stock) =
                oldItem.symbol == newItem.symbol

            override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean =
                oldItem == newItem
        }
    }
}