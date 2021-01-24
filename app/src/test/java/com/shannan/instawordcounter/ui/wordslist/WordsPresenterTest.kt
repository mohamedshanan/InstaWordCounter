package com.shannan.instawordcounter.ui.wordslist

import com.shannan.instawordcounter.data.FakeRepository
import org.junit.Before
import org.junit.Test

class WordsPresenterTest {
    // Subject under test
    private lateinit var wordsPresenter: WordsPresenter

    // Use a fake repository to be injected into the presenter
    private lateinit var wordsRepository: FakeRepository

    @Before
    fun setupViewModel() {
        wordsRepository = FakeRepository()
    }

    @Test
    fun loadEmptyWords_error() {

        wordsRepository.wordsData = ""

        wordsPresenter = WordsPresenter(object : WordsListContract.View {
            override fun onWordsFetched(words: Array<Pair<String, Int>>) {
                println("words fetched")
            }

            override fun onError() {
                println("Error")
                assert(true)
            }

            override fun setLoading(isLoading: Boolean) {
                println("Loading")
            }

            override fun checkConnectivity(): Boolean {
                return true
            }

        }, wordsRepository)


        // Make the repository return errors
        wordsRepository.setReturnError(false)

        wordsPresenter.getWordsList()
    }

    @Test
    fun loadWords_error() {

        wordsRepository.wordsData = "< HTML >\n" +
                "< HEAD >\n" +
                "< TITLE > title of page< /TITLE >\n" +
                "< /HEAD >\n" +
                "< BODY>\n" +
                "write what you like here: 'my first web page', or a piece about what you are reading, or a few thoughts on the course, or copy out a few words from a book or cornflake packet. Just type in your words using no extras such as bold, or italics, as these have special HTML tags, although you may use upper and lower case letters and single spaces. \n" +
                "< /BODY >\n" +
                "< /HTML >"

        wordsPresenter = WordsPresenter(object : WordsListContract.View {
            override fun onWordsFetched(words: Array<Pair<String, Int>>) {
                println("words fetched")
            }

            override fun onError() {
                println("Error")
                assert(true)
            }

            override fun setLoading(isLoading: Boolean) {
                println("Loading")
            }

            override fun checkConnectivity(): Boolean {
                return true
            }

        }, wordsRepository)


        // Make the repository return errors
        wordsRepository.setReturnError(true)

        wordsPresenter.getWordsList()
    }

    @Test
    fun loadWords_success() {

        wordsRepository.wordsData = "< HTML >\n" +
                "< HEAD >\n" +
                "< TITLE > title of page< /TITLE >\n" +
                "< /HEAD >\n" +
                "< BODY>\n" +
                "write what you like here: 'my first web page', or a piece about what you are reading, or a few thoughts on the course, or copy out a few words from a book or cornflake packet. Just type in your words using no extras such as bold, or italics, as these have special HTML tags, although you may use upper and lower case letters and single spaces. \n" +
                "< /BODY >\n" +
                "< /HTML >"

        wordsPresenter = WordsPresenter(object : WordsListContract.View {
            override fun onWordsFetched(words: Array<Pair<String, Int>>) {
                println("words fetched")
                assert(words.isNotEmpty())
            }

            override fun onError() {
                println("Error")
            }

            override fun setLoading(isLoading: Boolean) {
                println("Loading")
            }

            override fun checkConnectivity(): Boolean {
                return true
            }

        }, wordsRepository)


        wordsRepository.setReturnError(false)

        wordsPresenter.getWordsList()

    }
}