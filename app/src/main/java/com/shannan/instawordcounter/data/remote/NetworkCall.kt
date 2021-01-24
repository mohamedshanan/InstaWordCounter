package com.shannan.instawordcounter.data.remote

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NetworkCall {
    fun makeNetworkCall(urlString: String?): String? {
        var connection: HttpURLConnection? = null
        var response: String? = null
        var inputStream: InputStream? = null
        try {
            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            inputStream = connection.inputStream
            response = readIt(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (inputStream != null) {
                    connection!!.disconnect()
                    inputStream.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return response
    }

    @Throws(Exception::class)
    private fun readIt(iStream: InputStream?): String {
        var singleLine: String?
        val totalLines = StringBuilder(iStream!!.available())
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(iStream))
            while (reader.readLine().also { singleLine = it } != null) {
                totalLines.append(singleLine)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return totalLines.toString()
    }
}