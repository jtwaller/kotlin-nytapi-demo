package com.jtwaller.nytarticlesearchdemo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.jtwaller.nytarticlesearchdemo.api.ArticleSearchObject
import com.jtwaller.nytarticlesearchdemo.api.NytRestApi
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(val api: NytRestApi): ViewModel() {

    private lateinit var articles: MutableLiveData<List<NytArticle>>

    fun getArticles(query: String, page: Int = 0): LiveData<List<NytArticle>> {
        if (!::articles.isInitialized) {
            articles = MutableLiveData()
            loadArticles(query, page)
        }

        return articles
    }

    private fun loadArticles(query: String, page: Int) {
        api.getArticles(query, page).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object: Observer<ArticleSearchObject> {
                    override fun onComplete() {
                        Log.d(MainActivity.TAG, ": complete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d(MainActivity.TAG, ": subbed")
                    }

                    override fun onNext(t: ArticleSearchObject) {
                        Log.d(MainActivity.TAG, ": next")

                        for (doc in t.response.docs) {
                            Log.d("viewmodel", ": " + doc.headline.main)
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.d(MainActivity.TAG, ": error -> " + e.printStackTrace())
                    }
                })
    }
}
