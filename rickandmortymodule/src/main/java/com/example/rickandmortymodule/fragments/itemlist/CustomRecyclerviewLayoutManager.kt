package com.example.rickandmortymodule.fragments.itemlist

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class CustomRecyclerviewLayoutManager(context: Context?) :
    LinearLayoutManager(context) {
    private var isScrollEnabled = true
    fun setScrollEnabled(flag: Boolean) {
        isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }

    override fun canScrollHorizontally(): Boolean {
        return isScrollEnabled && super.canScrollHorizontally()
    }
}