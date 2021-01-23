package com.shannan.instawordcounter.ui.wordslist

interface WordsListContract {

    interface View {
        fun onWordsFetched(words: Array<Pair<String, Int>>)
        fun onError()
        fun setLoading(isLoading: Boolean)
        fun checkConnectivity(): Boolean
    }

    interface Presenter {
        fun getWordsList()
        fun onDestroy()
    }
}