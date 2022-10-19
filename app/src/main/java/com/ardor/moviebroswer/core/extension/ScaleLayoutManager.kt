package com.ardor.moviebroswer.core.extension

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.abs

class ScaleLayoutManager : LinearLayoutManager {
    private val mShrinkAmount = 0.5f
    private val mShrinkDistance = 0.9f

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {}

    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: RecyclerView.State): Int {
        val orientation = orientation
        return if (orientation == HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)
            scaleChildren()
            scrolled
        } else 0
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        scaleChildren()
    }

    private fun scaleChildren() {
        val midpoint = width / 2f
        val d1 = mShrinkDistance * midpoint
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childMidpoint = (getDecoratedRight(child!!) + getDecoratedLeft(child)) / 2f
            val d = d1.coerceAtMost(abs(midpoint - childMidpoint))
            val scale = 1f + (1f - mShrinkAmount - 1f) * (d -  0f) / (d1 -  0f)
            child.scaleX = scale
            child.scaleY = scale
        }
    }
}