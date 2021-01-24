package com.shannan.instawordcounter.data


/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
class FakeRepository : WordsRepository {

    var wordsData: String = ""

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override fun getWords(forceUpdate: Boolean, clientCallBack: CallBack) {
        if (shouldReturnError) {
            clientCallBack.onError()
        } else {
            clientCallBack.onSuccess(wordsData)
        }
    }
}
