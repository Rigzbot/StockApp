package com.rishik.stockapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rishik.stockapp.R
import com.rishik.stockapp.databinding.NewsItemBinding
import com.rishik.stockapp.domain.News
import android.content.Intent

@SuppressLint("StaticFieldLeak")
private var context: Context? = null

class NewsClick(val block: (News) -> Unit) {
    /**
     * Called when a news is clicked
     *
     * @param news the news that was clicked
     */
    fun onClick(news: News) = block(news)
}

class NewsAdapter(private val callback: NewsClick) : RecyclerView.Adapter<NewsViewHolder>() {
    var news: List<News> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val withDataBinding: NewsItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            NewsViewHolder.LAYOUT,
            parent, false
        )
        context = parent.context
        return NewsViewHolder(withDataBinding)
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        holder.viewDataBinding.also {
            it.news = news[position]
            it.newsCallback = callback
            it.shareButton.setOnClickListener {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plain"
                i.putExtra(Intent.EXTRA_SUBJECT, "I found this great article on the StockX App")
                i.putExtra(Intent.EXTRA_TEXT, news[position].url)
                context!!.startActivity(Intent.createChooser(i, "Share URL"))
            }
        }
    }
}

class NewsViewHolder(val viewDataBinding: NewsItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.news_item
    }
}