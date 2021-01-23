package com.shannan.instawordcounter

import java.util.*

class WordCounter {
    fun countWords(input: String?): Map<String, String?> {
        val wordsMap: MutableMap<String, String?> = HashMap()
        input?.split(" ")?.toTypedArray()?.map {
            if (it.isNotEmpty()) {
                it.toLowerCase(Locale.getDefault())
                if (wordsMap.containsKey(it)) {
                    val count = wordsMap[it]!!.toInt()
                    wordsMap[it] = (count + 1).toString()
                } else {
                    wordsMap[it] = "1"
                }
            }
        }
        return wordsMap
    }
}