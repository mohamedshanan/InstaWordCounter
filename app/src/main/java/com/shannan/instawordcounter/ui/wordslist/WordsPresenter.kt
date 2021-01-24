package com.shannan.instawordcounter.ui.wordslist


import com.shannan.instawordcounter.data.CallBack
import com.shannan.instawordcounter.data.WordsRepository

class WordsPresenter(
    view: WordsListContract.View,
    private val wordsRepository: WordsRepository
) : WordsListContract.Presenter {

    private val wordCounter: WordCounter = WordCounter()

    private var view: WordsListContract.View? = view

    override fun onDestroy() {
        this.view = null
    }

    override fun getWordsList() {
        view?.setLoading(true)
        view?.checkConnectivity()?.let {
            wordsRepository.getWords(it, object : CallBack {
                override fun onSuccess(response: String?) {
                    view?.setLoading(false)
                    if (response.isNullOrEmpty()) {
                        view?.onError()
                    }
                    val body = response
                        // get only the page body
                        ?.substringAfter("<body>")
                        ?.substringBefore("</body>")
                        // remove style tags
                        ?.replace("<style([\\s\\S]+?)</style>".toRegex(), " ")
                        // remove html tags
                        ?.replace("<(.|\\n)*?>".toRegex(), " ")
                        // remove special characters
                        ?.replace("[^A-Za-z0-9 ]".toRegex(), "")

                    val wordsArray: Array<Pair<String, Int>> =
                        wordCounter.countWords(body).toList().toTypedArray()
                    view?.onWordsFetched(wordsArray)
                }

                override fun onError() {
                    view?.setLoading(false)
                    view?.onError()
                }

            })
        }
    }
}
