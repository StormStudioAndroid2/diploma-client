package com.example.androidreader.pageView.model

data class DedicatedPageRect(
    var left: Float,
    var right: Float,
    var top: Float,
    var bottom: Float,
    var word: String
) : Comparable<DedicatedPageRect> {
    override fun compareTo(other: DedicatedPageRect): Int {
        if (Math.abs(bottom - other.bottom) > 0.01f) {
            return (bottom - other.bottom).toInt()
        }
        return (right - other.right).toInt()
    }

    fun isInner(other: DedicatedPageRect): Boolean {
        return (this.left > other.left && this.right < other.right && this.top > other.top && this.bottom < other.bottom)
    }

    companion object {
        fun empty() = DedicatedPageRect(
            0f, 0f, 0f, 0f, ""
        ).copy()
    }
}