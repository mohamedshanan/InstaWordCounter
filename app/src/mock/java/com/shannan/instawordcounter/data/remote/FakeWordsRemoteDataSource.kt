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
package com.shannan.instawordcounter.data.remote

import android.os.Handler
import com.shannan.instawordcounter.data.CallBack
import com.shannan.instawordcounter.data.WordsDataSource

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
object FakeWordsRemoteDataSource : WordsDataSource {

    private val content = "< HTML >\n" +
            "< HEAD >\n" +
            "< TITLE > title of page< /TITLE >\n" +
            "< /HEAD >\n" +
            "< BODY>\n" +
            "write what you like here: 'my first web page', or a piece about what you are reading, or a few thoughts on the course, or copy out a few words from a book or cornflake packet. Just type in your words using no extras such as bold, or italics, as these have special HTML tags, although you may use upper and lower case letters and single spaces. \n" +
            "< /BODY >\n" +
            "< /HTML >"

    override fun getWords(callBack: CallBack) {
        Handler().postDelayed({ callBack.onSuccess(content) }, 3000)
    }

    override fun save(content: String?) {

    }
}
