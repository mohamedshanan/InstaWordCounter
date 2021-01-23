package com.shannan.instawordcounter.ui.wordslist

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shannan.instawordcounter.R
import com.shannan.instawordcounter.di.DependencyInjectorImpl


class WordsActivity : AppCompatActivity(), WordsListContract.View {

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private lateinit var progressView: View
    private lateinit var errorView: ViewGroup
    private lateinit var errorActionButton: TextView
    private lateinit var presenter: WordsListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        presenter = WordsPresenter(this, cacheDir, DependencyInjectorImpl())
        presenter.getWordsList()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        progressView = findViewById(R.id.layout_progress_bar)
        errorView = findViewById(R.id.layout_error_view)
        errorActionButton = findViewById(R.id.layout_error_action)
    }

    override fun onWordsFetched(words: Array<Pair<String, Int>>) {
        progressView.visibility = View.GONE
        errorView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

        adapter = RecyclerAdapter(words)
        recyclerView.adapter = adapter
    }

    override fun onError() {
        progressView.visibility = View.GONE
        recyclerView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        errorActionButton.setOnClickListener { presenter.getWordsList() }
    }

    override fun setLoading(isLoading: Boolean) {
        errorView.visibility = View.GONE
        if (isLoading) {
            progressView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            progressView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun checkConnectivity(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}