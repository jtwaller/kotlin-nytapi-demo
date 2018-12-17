package com.jtwaller.nytarticlesearchdemo

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.widget.SearchView
import com.jtwaller.nytarticlesearchdemo.commons.androidLazy
import com.jtwaller.nytarticlesearchdemo.commons.getViewModel
import com.jtwaller.nytarticlesearchdemo.di.ViewModelFactory
import com.jtwaller.nytarticlesearchdemo.recyclerview.ArticleListAdapter
import com.jtwaller.nytarticlesearchdemo.recyclerview.ArticlesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<ArticlesViewModel>
    private val articlesViewModel by androidLazy {
        getViewModel<ArticlesViewModel>(viewModelFactory)
    }

    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NytApp.networkComponent.inject(this)

        val adapter = ArticleListAdapter {
            launchArticleDetailActivity(this@MainActivity, it.url)
        }

        article_recycler_view.layoutManager = LinearLayoutManager(this)
        article_recycler_view.adapter = adapter

        // TODO: Implement loading indicator when loading articles

        articlesViewModel.articles.observe(this, Observer {
            adapter.loadItems(it ?: emptyList())
            adapter.notifyDataSetChanged()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        searchView = menu.findItem(R.id.search_view).actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 == null) return false

                searchView.clearFocus()
                articlesViewModel.getArticles(p0)

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun launchArticleDetailActivity(context: Context, url: String) {
        val intent = Intent(context, ArticleDetailActivity::class.java)
        intent.putExtra("url", url)

        startActivity(intent)
    }

}
