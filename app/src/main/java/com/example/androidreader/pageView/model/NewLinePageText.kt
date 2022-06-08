package com.example.androidreader.pageView.model

import android.graphics.Color
import com.example.androidreader.pageView.model.PageText

class NewLinePageText : PageText {
    override var text: String = "\n"
    override var color = Color.BLACK
    override var pageSpacing = 3f
    override var size = 0f
}