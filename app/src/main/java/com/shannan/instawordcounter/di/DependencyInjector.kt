package com.shannan.instawordcounter.di

import com.shannan.instawordcounter.data.Cache
import com.shannan.instawordcounter.data.GetContentOperation
import com.shannan.instawordcounter.data.TaskExecutor
import com.shannan.instawordcounter.ui.wordslist.WordCounter

interface DependencyInjector {
    fun cache(): Cache
    fun getContentOperation(): GetContentOperation
    fun taskExecutor(): TaskExecutor
    fun wordCounter(): WordCounter
    //  fun networkCall() : NetworkCall
}
