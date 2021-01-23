package com.shannan.instawordcounter.ui.wordslist

import com.shannan.instawordcounter.data.Cache
import com.shannan.instawordcounter.data.GetContentOperation
import com.shannan.instawordcounter.data.TaskExecutor
import com.shannan.instawordcounter.di.DependencyInjector
import java.io.File

class WordsPresenter(
    view: WordsListContract.View,
    private var cacheDir: File,
    dependencyInjector: DependencyInjector
) : WordsListContract.Presenter {

    private val cache: Cache = dependencyInjector.cache()
    private val getContentOperation: GetContentOperation = dependencyInjector.getContentOperation()
    private val taskExecutor: TaskExecutor = dependencyInjector.taskExecutor()
    private val wordCounter: WordCounter = dependencyInjector.wordCounter()

    private var view: WordsListContract.View? = view

    override fun onDestroy() {
        this.view = null
    }

    override fun getWordsList() {
        if (view?.checkConnectivity()!!) {
            loadWordsList()
        } else {
            val wordsFromCache: Map<String, Int> = cache.read(cacheDir)
            if (wordsFromCache.isNotEmpty()) {
                val wordsArray: Array<Pair<String, Int>> =
                    wordsFromCache.toList().toTypedArray()
                view?.onWordsFetched(wordsArray)
            } else {
                view?.onError()
            }
        }
    }

    private fun loadWordsList() {
        taskExecutor.executeProgressTask(
            getContentOperation,
            onProgress = { progress ->
                view?.setLoading(progress)
            },
            onComplete = { result ->
                if (result.isNullOrEmpty()) {
                    view?.onError()
                }
                val body = result
                    // get only the page body
                    ?.substringAfter("<body>")
                    ?.substringBefore("</body>")
                    // remove style tags
                    ?.replace("<style([\\s\\S]+?)</style>".toRegex(), " ")
                    // remove html tags
                    ?.replace("<(.|\\n)*?>".toRegex(), " ")
                    // remove special characters
                    ?.replace("[^A-Za-z0-9 ]".toRegex(), "")

                cache.write(cacheDir, wordCounter.countWords(body))
                val wordsFromCache: Map<String, Int> = cache.read(cacheDir)
                val wordsArray: Array<Pair<String, Int>> = wordsFromCache.toList().toTypedArray()
                view?.onWordsFetched(wordsArray)
            }
        )
    }
}
