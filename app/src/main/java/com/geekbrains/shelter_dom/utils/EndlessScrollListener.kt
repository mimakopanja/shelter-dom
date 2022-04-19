package com.geekbrains.shelter_dom.utils

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EndlessScrollListener(
    private val gridLayout: GridLayoutManager,
    private val onLoadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

    var loading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = gridLayout.itemCount
        val lastVisibleItemPosition = gridLayout.findLastVisibleItemPosition()

        Log.d("EndlessScrollListener", "onScrolled: $totalItemCount")
        Log.d("EndlessScrollListener", "onScrolled: $lastVisibleItemPosition")
        Log.d(
            "EndlessScrollListener",
            "onScrolled: " + (lastVisibleItemPosition + VISIBLE_THRESHOLD)
        )

        val endHasBeenReached = lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount
        if (!loading && totalItemCount > 0 && endHasBeenReached) {
            loading = true
            onLoadMore()
        }
    }
}