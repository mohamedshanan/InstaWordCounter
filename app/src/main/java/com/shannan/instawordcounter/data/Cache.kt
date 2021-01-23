package com.shannan.instawordcounter.data

import java.io.*

class Cache {

    private val wordsCacheFileName = "wordsCache.txt"

    fun write(cacheDir: File, wordsMap: Map<String, Int>?) {

        val cacheFile = File(cacheDir, wordsCacheFileName)
        if (!cacheFile.exists()) {
            try {
                cacheFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        val f = FileOutputStream(cacheFile)
        val s = ObjectOutputStream(f)
        s.writeObject(wordsMap)
        s.close()
    }

    fun read(cacheDir: File): Map<String, Int> {
        val cacheFile = File(cacheDir, wordsCacheFileName)
        var wordsMap: MutableMap<String, Int> = HashMap()

        if (cacheFile.exists()) {
            val f1 = FileInputStream(cacheFile)
            val s1 = ObjectInputStream(f1)
            wordsMap =
                s1.readObject() as HashMap<String, Int>
            s1.close()
        }
        return wordsMap
    }
}