package com.ksd.khincurrency.binding

import android.text.InputFilter
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setCharLimit")
fun android.widget.SearchView.setCharLimit(count: Int) {
    val id = resources.getIdentifier("android:id/search_src_text", null, null)
    findViewById<TextView>(id).apply {
        filters = arrayOf<InputFilter>(InputFilter.LengthFilter(count))
    }
}