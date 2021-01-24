package com.shannan.instawordcounter

import android.app.Application
import com.shannan.instawordcounter.data.WordsRepository
import com.shannan.instawordcounter.di.ServiceLocator

class InstaWordsApplication : Application() {

    // Depends on the flavor,
    val wordsRepository: WordsRepository
        get() = ServiceLocator.provideWordsRepository(this)
}
