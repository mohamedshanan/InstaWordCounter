/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shannan.instawordcounter.di

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.shannan.instawordcounter.data.DefaultWordsRepository
import com.shannan.instawordcounter.data.WordsDataSource
import com.shannan.instawordcounter.data.WordsRepository
import com.shannan.instawordcounter.data.local.FileCache
import com.shannan.instawordcounter.data.local.WordsLocalDataSource
import com.shannan.instawordcounter.data.remote.GetContentOperation
import com.shannan.instawordcounter.data.remote.TaskExecutor
import com.shannan.instawordcounter.data.remote.WordsRemoteDataSource

/**
 * A Service Locator for the [WordsRepository]. This is the prod version, with a
 * the "real" [WordsRemoteDataSource].
 */
object ServiceLocator {

    private val lock = Any()
    private var fileCache: FileCache? = null
    private var tasExecutor: TaskExecutor? = null
    private var getContentOperation: GetContentOperation? = null

    @Volatile
    var wordsRepository: WordsRepository? = null
        @VisibleForTesting set

    fun provideWordsRepository(context: Context): WordsRepository {
        synchronized(this) {
            return wordsRepository ?: wordsRepository ?: createWordsRepository(context)
        }
    }

    private fun createWordsRepository(context: Context): WordsRepository {
        val taskExecutor = tasExecutor ?: TaskExecutor()
        val getContentOperation = getContentOperation ?: GetContentOperation()
        val newRepo = DefaultWordsRepository(
            WordsRemoteDataSource(
                taskExecutor,
                getContentOperation
            ), createWordsLocalDataSource(context)
        )
        wordsRepository = newRepo
        return newRepo
    }

    private fun createWordsLocalDataSource(context: Context): WordsDataSource {
        val fileCache = fileCache ?: createFileCache(context)
        return WordsLocalDataSource(
            fileCache
        )
    }

    private fun createFileCache(context: Context): FileCache {
        return FileCache(context.cacheDir)
    }
}
