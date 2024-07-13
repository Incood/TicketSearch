package com.example.presentation

object ImageMapping {
    private val imageMap = mapOf(
        1 to R.drawable.image1,
        2 to R.drawable.image2,
        3 to R.drawable.image3
    )

    fun getImageResource(id: Int): Int {
        return imageMap[id] ?: R.drawable.ic_placeholder
    }
}