package com.shannan.instawordcounter.di

import com.shannan.instawordcounter.data.Cache
import com.shannan.instawordcounter.data.GetContentOperation
import com.shannan.instawordcounter.data.TaskExecutor
import com.shannan.instawordcounter.ui.wordslist.WordCounter

class DependencyInjectorImpl : DependencyInjector {

    override fun cache(): Cache {
        return Cache()
    }

    override fun getContentOperation(): GetContentOperation {
        return GetContentOperation()
    }

    override fun taskExecutor(): TaskExecutor {
        return TaskExecutor()
    }

    override fun wordCounter(): WordCounter {
        return WordCounter()
    }
}
