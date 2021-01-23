package com.shannan.instawordcounter.data

import java.io.*

class Cache {

    private val wordsCacheFileName = "wordsCache.txt"

    fun write(cacheDir: File, wordsMap: Map<String, String>?) {

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

    fun read(cacheDir: File): Map<String, String> {
        val cacheFile = File(cacheDir, wordsCacheFileName)
        var fileObj2: MutableMap<String, String> = HashMap()

        if (cacheFile.exists()) {
            val f1 = FileInputStream(cacheFile)
            val s1 = ObjectInputStream(f1)
            fileObj2 =
                s1.readObject() as HashMap<String, String>
            s1.close()
        }
        return fileObj2
    }
}