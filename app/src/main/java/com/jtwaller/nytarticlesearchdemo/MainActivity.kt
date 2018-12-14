package com.jtwaller.nytarticlesearchdemo

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.ListView
import android.widget.SearchView
import com.jtwaller.nytarticlesearchdemo.di.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    lateinit var searchView: SearchView
    lateinit var listView: ListView

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<ArticlesViewModel>
    private val mainViewModel by androidLazy {
        getViewModel<ArticlesViewModel>(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NytApp.networkComponent.inject(this)
        listView = findViewById(R.id.list_view)

        loadArticles("godzilla")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        searchView = menu.findItem(R.id.search_view).actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 == null) return false

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        return true
    }

    fun loadArticles(query: String, page: Int = 0) {
        mainViewModel.getArticles(query, page).observe(this, Observer<List<NytArticle>>{ articles ->
            if (articles == null) return@Observer
            listView.adapter = ArticleListAdapter(this, articles)
        })
    }

}
