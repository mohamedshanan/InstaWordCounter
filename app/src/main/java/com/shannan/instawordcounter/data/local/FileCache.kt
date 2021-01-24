package com.shannan.instawordcounter.data.local

import java.io.*

class FileCache(private val cacheDir: File) {

    private val wordsCacheFileName = "wordsCache.txt"

    @Throws(Exception::class)
    fun write(wordsMap: String?) {

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

    @Throws(Exception::class)
    fun read(): String {
        val cacheFile = File(cacheDir, wordsCacheFileName)
        var content = ""

        if (cacheFile.exists()) {
            val f1 = FileInputStream(cacheFile)
            val s1 = ObjectInputStream(f1)
            content =
                s1.readObject() as String
            s1.close()
        }
        return content
    }
}