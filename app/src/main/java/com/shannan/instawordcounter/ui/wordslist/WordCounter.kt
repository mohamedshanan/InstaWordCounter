package com.shannan.instawordcounter.ui.wordslist

import java.util.*

class WordCounter {
    fun countWords(input: String?): Map<String, Int> {
        val wordsMap: MutableMap<String, Int> = HashMap()
        var word: String
        input?.split(" ")?.toTypedArray()?.map {
            if (it.isNotEmpty()) {
                word = it.toLowerCase(Locale.getDefault())
                if (wordsMap.containsKey(word)) {
                    val count = wordsMap[word]!!.toInt()
                    wordsMap[word] = count + 1
                } else {
                    wordsMap[word] = 1
                }
            }
        }
        return wordsMap
    }
}