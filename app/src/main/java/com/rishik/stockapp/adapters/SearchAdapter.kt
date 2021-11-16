package com.rishik.stockapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rishik.stockapp.R
import com.rishik.stockapp.databinding.SearchItemBinding
import com.rishik.stockapp.domain.Stocks

class SearchAdapter: RecyclerView.Adapter<SearchViewHolder>() {
    var stocks: List<Stocks> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val withDataBinding: SearchItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            SearchViewHolder.LAYOUT,
            parent, false
        )
        return SearchViewHolder(withDataBinding)
    }

    override fun getItemCount() = stocks.size

    override fun onBindViewHolder(
        holder: SearchViewHolder,
        position: Int
    ) {
        holder.viewDataBinding.also {
            it.stock = stocks[position]
        }
    }
}

class SearchViewHolder(val viewDataBinding: SearchItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.search_item
    }
}