package com.example.androidreader.pageView.model

import android.graphics.Color
import com.example.androidreader.pageView.model.PageText

class ParagraphPageText(
    override var text: String,
) : PageText {
    override var size: Float = 30f
    override var color: Int = Color.BLACK
    override var pageSpacing = 5f
}