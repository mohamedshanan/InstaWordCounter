package com.shannan.instawordcounter.data

class DefaultWordsRepository(
    private val wordsRemoteDataSource: WordsDataSource,
    private val wordsLocalDataSource: WordsDataSource,
) : WordsRepository {

    override fun getWords(forceUpdate: Boolean, clientCallBack: CallBack) {
        if (forceUpdate) {
            try {
                updateContentFromRemoteDataSource(clientCallBack)
            } catch (ex: Exception) {
                clientCallBack.onError()
            }
        } else {
            wordsLocalDataSource.getWords(object : CallBack {
                override fun onSuccess(response: String?) {
                    if (!response.isNullOrEmpty()) {
                        clientCallBack.onSuccess(response)
                    } else {
                        clientCallBack.onError()
                    }
                }

                override fun onError() {
                    clientCallBack.onError()
                }
            })
        }
    }

    private fun updateContentFromRemoteDataSource(clientCallBack: CallBack) {
        wordsRemoteDataSource.getWords(object : CallBack {
            override fun onSuccess(response: String?) {
                if (!response.isNullOrEmpty()) {
                    clientCallBack.onSuccess(response)
                    wordsLocalDataSource.save(response)
                } else {
                    clientCallBack.onError()
                }
            }

            override fun onError() {
                clientCallBack.onError()
            }
        })
    }
}