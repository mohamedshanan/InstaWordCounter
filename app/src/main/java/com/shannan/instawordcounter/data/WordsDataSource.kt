package com.shannan.instawordcounter.data

interface WordsDataSource {
    fun getWords(callBack: CallBack)
    fun save(content: String?)
}