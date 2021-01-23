package com.shannan.instawordcounter.ui.wordslist

import org.junit.Test

class WordCounterTest {

    @Test
    fun testNullWords() {
        val map: Map<String, Int> = WordCounter().countWords(null)
        assert(map.isEmpty())
    }

    @Test
    fun testEmptyWords() {
        val map: Map<String, Int> = WordCounter().countWords("")
        assert(map.isEmpty())
    }

    @Test
    fun testOneWord() {
        val word = "test"
        val map: Map<String, Int> = WordCounter().countWords(word)
        assert(map.keys.size == 1)
        assert(map.containsKey(word))
        assert(map[word] == 1)
    }

    @Test
    fun testCounterIgnoreCase() {
        val words = "test TEST"
        val map: Map<String, Int> = WordCounter().countWords(words)
        assert(map.keys.size == 1)
        assert(map.containsKey("test"))
        assert(map["test"] == 2)
    }

    @Test
    fun testShortWords() {
        val words =
            "Ship Quality Apps with Real-Time Contextual Insights Instabug empowers mobile teams to release with confidence through comprehensive bug and crash reports, performance monitoring, and real-time user surveys and feedback."
        val map: Map<String, Int> = WordCounter().countWords(words)
        assert(map.isNotEmpty())
        assert(words.split(" ").size == map.values.sum())
        assert(map["with"] == 2)
        assert(map["real-time"] == 2)
    }
}

