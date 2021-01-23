package com.shannan.instawordcounter.ui.wordslist

interface WordsListContract {
    interface Presenter {
        val wordsList: Unit
    }

    interface View {
        fun onWordsFetched(words: Map<String, Any>)
        fun onFailedResponse(message: String)
        fun setLoading(isLoading: Boolean)
    }
}