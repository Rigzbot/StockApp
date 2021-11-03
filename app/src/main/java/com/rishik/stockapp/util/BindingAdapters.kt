package com.rishik.stockapp.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.rishik.stockapp.R
import android.text.format.DateUtils

private const val bloomUrl = "https://data.bloomberglp.com/company/sites/2/2019/01/logobbg-wht.png"
private const val marketWatchUrl = "https://mw3.wsj.net/mw5/content/logos/mw_logo_social.png"

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}

@BindingAdapter("sourceLogo")
fun setLogoUrl(imageView: ImageView, source: String) {
    val url: String = when (source) {
        "Bloomberg" -> bloomUrl
        "MarketWatch" -> marketWatchUrl
        else -> "cnbc"
    }
    if (url == "cnbc") {
        imageView.setImageResource(R.drawable.cnbclogo)
    } else {
        Glide.with(imageView.context).load(url).into(imageView)
    }
}

@BindingAdapter("timeAgo")
fun setTimeAgo(textView: TextView, time: Long) {
    val startTime = time * 1000
    var timeAgo = DateUtils.getRelativeTimeSpanString(startTime)
    timeAgo = "●    $timeAgo"
    textView.text = timeAgo.toString()
}