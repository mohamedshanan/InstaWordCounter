/*
 * Copyright (C) 2019 The Android Open Source Project
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
package com.shannan.instawordcounter.data.local

import com.shannan.instawordcounter.data.CallBack
import com.shannan.instawordcounter.data.WordsDataSource

/**
 * Concrete implementation of a data source as a file cache.
 */

class WordsLocalDataSource internal constructor(
    private val fileCache: FileCache,
) : WordsDataSource {

    override fun getWords(callBack: CallBack) {
        return try {
            callBack.onSuccess(fileCache.read())
        } catch (e: Exception) {
            callBack.onError()
        }
    }

    override fun save(content: String?) {
        try {
            fileCache.write(content)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }
}
