package com.shannan.instawordcounter.data

interface WordsRepository {
    fun getWords(forceUpdate: Boolean = false, clientCallBack: CallBack)
}