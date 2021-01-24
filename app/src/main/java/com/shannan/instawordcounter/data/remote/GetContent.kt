package com.shannan.instawordcounter.data.remote

class GetContentOperation : ProgressTask<Boolean, String>() {

    private val url = "https://instabug.com"

    override fun runTask(progressToken: OnTaskProgressUpdate<Boolean>): String? {
        progressToken(true)
        val networkCall = NetworkCall()
        val response = networkCall.makeNetworkCall(url)
        progressToken(false)
        return response
    }
}