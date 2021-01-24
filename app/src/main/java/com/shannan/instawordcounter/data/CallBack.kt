package com.shannan.instawordcounter.data

interface CallBack {
    fun onSuccess(response: String?)
    fun onError()
}