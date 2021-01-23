package com.shannan.instawordcounter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shannan.instawordcounter.data.GetContentOperation
import com.shannan.instawordcounter.data.TaskExecutor


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readIt()
    }

    private fun readIt() {
        val taskExecutor = TaskExecutor()
        val counter = WordCounter()

        taskExecutor.executeProgressTask(
            GetContentOperation(),
            onProgress = { progress ->
                println("progress: $progress")
            },
            onComplete = { result ->

                val body = result
                    // get only the page body
                    .substringAfter("<body>")
                    .substringBefore("</body>")
                    // remove style tags
                    .replace("<style([\\s\\S]+?)</style>".toRegex(), " ")
                    // remove html tags
                    .replace("<(.|\\n)*?>".toRegex(), " ")
                    // remove special characters
                    .replace("[^A-Za-z0-9 ]".toRegex(), "")

                for ((k, v) in counter.countWords(body)) {
                    println("$k = $v")
                }
            }
        )
    }
}