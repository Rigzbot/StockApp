package com.rishik.stockapp.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import android.text.format.DateUtils
import android.view.ViewGroup
import android.widget.LinearLayout

private const val bloomUrl = "https://data.bloomberglp.com/company/sites/2/2019/01/logobbg-wht.png"
private const val marketWatchUrl = "https://mw3.wsj.net/mw5/content/logos/mw_logo_social.png"

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
        imageView.setImageResource(com.rishik.stockapp.R.drawable.cnbclogo)
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

//@BindingAdapter("shareArticle")
//fun shareArticle(imageView: ImageView, url: String) {
//    val i = Intent(Intent.ACTION_SEND)
//    i.type = "text/plain"
//    i.putExtra(Intent.EXTRA_SUBJECT, "I found this great article on the StockX App")
//    i.putExtra(Intent.EXTRA_TEXT, url)
//    imageView.context.startActivity(Intent.createChooser(i, "Share URL"))
//}

@BindingAdapter("setMargin")
fun setMargin(linearLayout: LinearLayout, searching: Boolean) {
    val layoutParams: ViewGroup.MarginLayoutParams = linearLayout.layoutParams as ViewGroup.MarginLayoutParams
    if(searching){
        layoutParams.leftMargin = 8
        layoutParams.rightMargin = 8
    } else {
        layoutParams.leftMargin = 48
        layoutParams.rightMargin = 48
    }
    linearLayout.layoutParams = layoutParams
}