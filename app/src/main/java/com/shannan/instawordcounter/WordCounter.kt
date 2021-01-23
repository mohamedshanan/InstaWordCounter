package com.shannan.instawordcounter

import java.util.*

class WordCounter {
    fun countWords(input: String?): Map<String, String> {
        val wordsMap: MutableMap<String, String> = HashMap()
        var word: String
        input?.split(" ")?.toTypedArray()?.map {
            if (it.isNotEmpty()) {
                word = it.toLowerCase(Locale.getDefault())
                if (wordsMap.containsKey(word)) {
                    val count = wordsMap[word]!!.toInt()
                    wordsMap[word] = (count + 1).toString()
                } else {
                    wordsMap[word] = "1"
                }
            }
        }
        return wordsMap
    }
}