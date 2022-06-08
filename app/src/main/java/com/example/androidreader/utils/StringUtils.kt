package com.example.androidreader.utils

object StringUtils {
    val re = Regex("[^A-Za-z]")
    fun String.remainOnlySymbols() = this.replace(re, "")
}