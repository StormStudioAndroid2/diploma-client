package com.example.androidreader.pageView.converter

import com.example.androidreader.pageView.model.NewLinePageText
import com.example.androidreader.pageView.model.PageText
import com.example.androidreader.pageView.model.ParagraphPageText
import com.example.androidreader.pageView.model.TitlePageText
import com.example.androidreader.utils.Stemmer
import com.example.androidreader.utils.StringUtils.remainOnlySymbols

object PageTextConverter {

    fun convertStringToPageTextList(
        text: String,
        wordMap: MutableMap<String, String>
    ): List<PageText> {
        val stemmer = Stemmer()
        val list = mutableListOf<PageText>()
        val textList = text.split("\n")
        textList.forEach {
            if (it == "<empty>") {
                list.add(NewLinePageText())
            }
            if (it.containTag("p")) {
                list.add(ParagraphPageText(it.getTextFromTag("p")))
                it.getTextFromTag("p").split(" ").forEach { w ->
                    val s = stemmer.getStemmingWord(w.remainOnlySymbols())
                    if (wordMap.containsKey(s)) {
                        wordMap[w] = wordMap[s] ?: throw NoSuchElementException("no such element in map!")
                    }
                    if (w == "hobbits") {
                        if (wordMap.containsKey(s)) {
                            wordMap[w] = wordMap[s]
                                ?: throw NoSuchElementException("no such element in map!")
                        }
                    }
                }
            }
            if (it.containTag("title")) {
                list.add(TitlePageText(it.getTextFromTag("title")))
                it.getTextFromTag("title").split(" ").forEach { w ->
                    val s = stemmer.getStemmingWord(w)
                    if (wordMap.containsKey(s)) {
                        wordMap[w] = wordMap[s] ?: throw NoSuchElementException("no such element in map!")
                    }
                }
            }
        }
        return list
    }

    private fun String.getTextFromTag(tag: String): String =
        this.removePrefix("<$tag>").removeSuffix("</$tag>")


    private fun String.containTag(tag: String) = this.contains("<$tag>")
}