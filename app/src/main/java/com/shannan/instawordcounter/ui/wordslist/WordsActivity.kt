package com.shannan.instawordcounter.ui.wordslist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shannan.instawordcounter.R
import com.shannan.instawordcounter.data.Cache
import com.shannan.instawordcounter.data.GetContentOperation
import com.shannan.instawordcounter.data.TaskExecutor

class WordsActivity : AppCompatActivity(), WordsListContract.View {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        readIt()
    }

    private fun readIt() {

        TaskExecutor().executeProgressTask(
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

                Cache().write(cacheDir, WordCounter().countWords(body))
                val wordsFromCache: Map<String, Int> = Cache().read(cacheDir)
                val wordsArray: Array<Pair<String, Int>> = wordsFromCache.toList().toTypedArray()

                if (wordsArray.isNotEmpty()) {
                    adapter = RecyclerAdapter(wordsArray)
                    recyclerView.adapter = adapter
                }
            }
        )
    }

    override fun onWordsFetched(words: Map<String, Any>) {
        TODO("Not yet implemented")
    }

    override fun onFailedResponse(message: String) {
        TODO("Not yet implemented")
    }

    override fun setLoading(isLoading: Boolean) {
        TODO("Not yet implemented")
    }

}