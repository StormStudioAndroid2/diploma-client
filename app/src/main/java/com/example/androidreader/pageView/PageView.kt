package com.example.androidreader.pageView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import com.example.androidreader.R
import com.example.androidreader.pageView.listener.OnSwipeTouchListener
import com.example.androidreader.pageView.model.*
import com.example.androidreader.utils.StringUtils.remainOnlySymbols

class PageView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val textBound = Rect()

    private var rectF = RectF()

    private var initialString = EMPTY_TEXT

    private var linePadding: Float

    private var titlePadding: Float

    private var paragraphPadding: Float

    private var wordIterator: Iterator<String>? = null

    private var currentTopTextBound = LINE_SPACING

    private var wordMap: Map<String, String> = mapOf()

    @ColorInt
    private var pageColor: Int = Color.WHITE

    @ColorInt
    private var textColor: Int = Color.BLACK

    private var drawingPage: BookPage = BookPage(emptyList())

    private var pagesOfBook = listOf(BookPage(emptyList()))

    private var pageIterator = pagesOfBook.listIterator()

    private var dedicatedRectList = mutableListOf<DedicatedPageRect>()

    private var currentDedicatedRectListSize = 0

    private var paintTextParagraph = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textSize = 35f
    }
    private var paintTextTitle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textSize = 55f
    }

    private var paintStrangeWordRect = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.YELLOW
        textSize = 55f
    }

    private var wordListener: (word: String) -> Unit = {}

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PageView,
            R.attr.pageViewDefaultAttr, R.style.PageViewDefaultStyle
        ).apply {
            try {
                pageColor = getColor(R.styleable.PageView_pageColor, Color.WHITE)
                textColor = getColor(R.styleable.PageView_textColor, Color.BLACK)
                paragraphPadding =
                    getDimension(R.styleable.PageView_paragraphPadding, PARAGRAPH_SPACING)
                linePadding = getDimension(R.styleable.PageView_linePadding, LINE_SPACING)
                titlePadding = getDimension(R.styleable.PageView_titlePadding, TITLE_SPACING)
            } finally {
                recycle()
            }
        }
        this.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                turnToNextPage()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                turnToPreviousPage()
            }

            override fun onDouble(y: Float, x: Float) {
                super.onDouble(y, x)
                val rect = DedicatedPageRect(x, x, y, y, "")
                val searchWord = searchDedicatedWord(rect)
                searchWord?.let {
                    wordListener.invoke(wordMap[it] ?: "")
                }
            }
        })

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Math.min(w, h) - paintTextTitle.strokeWidth / 2
        rectF.set(
            paddingLeft.toFloat(),
            paddingTop.toFloat(),
            w.toFloat() - paddingRight, h.toFloat() - paddingBottom
        )

        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minHeight: Float =
            paintTextParagraph.textSize * MINIMUM_LINES + PARAGRAPH_SPACING * (MINIMUM_LINES - 1) + paddingEnd + paddingTop
        val minWidth: Float =
            paintTextParagraph.textSize * MINIMUM_LINE_LENGTH + paddingLeft + paddingRight
        val requestedWidth = Math.max(minWidth.toInt(), measuredWidth)
        val requestedHeight = Math.max(minHeight.toInt(), measuredHeight)
        val requestSize = Math.max(requestedHeight, requestedWidth)
        setMeasuredDimension(
            resolveSizeAndState(requestSize, widthMeasureSpec, 0),
            resolveSizeAndState(requestSize, heightMeasureSpec, 0)
        )
    }

    fun setHighlightedWordListener(listener: (word: String) -> Unit) {
        this.wordListener = listener
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { canvas ->
            currentDedicatedRectListSize = 0
            var currentTopTextBound = PAGE_SPACING + paddingTop
            drawingPage.textLineList.forEach { it ->
                var currentText = ""
                it.text.split(" ").forEachIndexed { ind, word ->
                    if (wordMap.contains(word.remainOnlySymbols())) {
                        val top = currentTopTextBound - it.topPadding
                        val left = rectF.left + it.paint.textBounds(currentText).right
                        val bottom = top + it.paint.textSize
                        val right = rectF.left + it.paint.textBounds(currentText + word).right
                        canvas.drawRect(
                            left,
                            top,
                            right,
                            bottom,
                            paintStrangeWordRect
                        )
                        dedicatedRectList[currentDedicatedRectListSize].left = left
                        dedicatedRectList[currentDedicatedRectListSize].top = top
                        dedicatedRectList[currentDedicatedRectListSize].right = right
                        dedicatedRectList[currentDedicatedRectListSize].bottom = bottom
                        dedicatedRectList[currentDedicatedRectListSize].word = word
                        currentDedicatedRectListSize++
                    }
                    currentText += "$word "
                }
                canvas.drawText(it.text, rectF.left, currentTopTextBound + it.topPadding, it.paint)
                currentTopTextBound += it.topPadding + it.paint.textSize
            }
        }
    }

    fun setPageText(pageText: List<PageText>) {
        this.pagesOfBook = convertToPages(convertToLines(pageText))
        this.pageIterator = pagesOfBook.listIterator()
        this.drawingPage = pageIterator.next()
        invalidate()
    }

    fun setWordMap(wordMap: Map<String, String>) {
        this.wordMap = wordMap
        this.dedicatedRectList = mutableListOf()
        wordMap.keys.forEach { _ ->
            dedicatedRectList.add(
                DedicatedPageRect(
                    0f, 0f, 0f, 0f, ""
                )
            )
        }
        invalidate()
    }

    fun turnToNextPage() {
        if (pageIterator.hasNext()) {
            val it = pageIterator.next()
            drawingPage = if (it == drawingPage && pageIterator.hasNext()) {
                pageIterator.next()
            } else {
                it
            }
            invalidate()
        }
    }

    fun turnToPreviousPage() {
        if (pageIterator.hasPrevious()) {
            val it = pageIterator.previous()
            drawingPage = if (it == drawingPage && pageIterator.hasPrevious()) {
                pageIterator.previous()
            } else {
                it
            }
            invalidate()
        }
    }

    private fun getTextForLine(currentWord: Iterator<String>, paint: Paint): String {
        var text = initialString
        initialString = EMPTY_TEXT
        while (currentWord.hasNext()) {
            val currentWordText = currentWord.next()
            if (paint.textBounds("$currentWordText$text ")
                    .width() + paddingLeft > rectF.right - paddingRight
            ) {
                initialString = "$currentWordText "
                return text
            }
            text += "$currentWordText "
        }
        return text
    }

    private fun Paint.textBounds(s: String): Rect {
        getTextBounds(s, 0, s.length, textBound)
        return textBound
    }

    private fun convertToPages(lines: List<TextLine>): List<BookPage> {
        val pageList = mutableListOf<BookPage>()
        var currentLineList = mutableListOf<TextLine>()
        var currentTopTextBound = PAGE_SPACING + paddingTop
        lines.forEach {
            if (currentTopTextBound + it.paint.textSize + it.topPadding < rectF.bottom - paddingBottom) {
                currentLineList.add(it)
                currentTopTextBound += it.paint.textSize + it.topPadding
            } else {
                pageList.add(BookPage(currentLineList))
                currentLineList = mutableListOf()
                currentTopTextBound = PAGE_SPACING + paddingTop
                currentLineList.add(it)
                currentTopTextBound += it.paint.textSize + it.topPadding
            }
        }
        return pageList
    }

    private fun convertToLines(pageTextList: List<PageText>): List<TextLine> {
        var currentPaint: Paint
        val list = mutableListOf<TextLine>()
        val currentPageText = pageTextList.iterator()
        while (currentPageText.hasNext()) {
            var elem = currentPageText.next()
            while (elem.text.isEmpty()) {
                elem = currentPageText.next()
            }
            var lineInterval = when (elem) {
                is ParagraphPageText -> {
                    paragraphPadding
                }
                is TitlePageText -> {
                    titlePadding
                }
                else -> {
                    paragraphPadding
                }
            }
            val wordList = elem.text.split(" ").toMutableList()
            currentPaint = when (elem) {
                is ParagraphPageText -> {
                    paintTextParagraph
                }
                is TitlePageText -> {
                    paintTextTitle
                }
                else -> {
                    paintTextParagraph
                }
            }

            currentTopTextBound += elem.pageSpacing
            wordIterator = wordList.iterator()
            wordIterator?.let {
                while (it.hasNext()) {
                    val text = getTextForLine(it, currentPaint)
                    list.add(TextLine(lineInterval, text, currentPaint))
                    lineInterval = linePadding
                }
            }
        }
        return list
    }

    fun searchDedicatedWord(rect: DedicatedPageRect): String? {
        if (currentDedicatedRectListSize == 0) {
            return null
        }
        var left = 0
        var right = currentDedicatedRectListSize - 1
        while (right - left > 1) {
            val m = left + (right - left) / 2
            if (dedicatedRectList[m].bottom > rect.bottom && dedicatedRectList[m].top > rect.top) {
                right = m
            } else {
                if (dedicatedRectList[m].bottom >= rect.bottom && dedicatedRectList[m].right > rect.right && dedicatedRectList[m].left > rect.left) {
                    right = m
                } else {
                    left = m
                }
            }
        }
        if (rect.isInner(dedicatedRectList[left])) {
            return dedicatedRectList[left].word
        }
        if (rect.isInner(dedicatedRectList[right])) {
            return dedicatedRectList[right].word
        }
        return null
    }

    companion object {
        const val PAGE_SPACING = 20f
        const val PARAGRAPH_SPACING = 15f
        const val TITLE_SPACING = 30f
        const val MINIMUM_LINES = 10
        const val MINIMUM_LINE_LENGTH = 50
        const val LINE_SPACING = 10f
        const val EMPTY_TEXT = ""
    }
}
