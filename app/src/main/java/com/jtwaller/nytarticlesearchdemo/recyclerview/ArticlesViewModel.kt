package com.jtwaller.nytarticlesearchdemo.recyclerview

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jtwaller.nytarticlesearchdemo.models.NytArticle
import com.jtwaller.nytarticlesearchdemo.api.ArticleSearchObject
import com.jtwaller.nytarticlesearchdemo.api.NytRestApi
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticlesViewModel @Inject constructor(val api: NytRestApi): ViewModel() {

    var articles: MutableLiveData<List<NytArticle>> = MutableLiveData()
    var query = "godzilla"
    var currentPage: Int = 0

    private val API_KEY: String = YOUR_KEY_HERE

    init {
        getArticles(query)
    }

    fun getArticles(newQuery: String, page: Int = 0) {
        query = newQuery
        currentPage = page

        api.getArticles(query, page, API_KEY).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object: Observer<ArticleSearchObject> {
                    override fun onComplete() {
                        // TODO: Dismiss loading indicator
                        // no-op
                    }

                    override fun onSubscribe(d: Disposable) {
                        // TODO: Create loading indicator
                        // no-op
                    }

                    override fun onNext(t: ArticleSearchObject) {
                        articles.postValue(t.response.docs.map { doc ->
                            val thumbs = doc.multimedia.filter { image -> image.subtype == "thumbnail" }
                            var thumb: String? = null
                            if (!thumbs.isEmpty()) {
                                thumb = "https://www.nytimes.com/" + thumbs.get(0).url
                            }

                            val title = doc.headline.main
                            val url = doc.webUrl

                            NytArticle(thumb, title, url)
                        })
                    }

                    override fun onError(e: Throwable) {
                        // TODO: Error handling
                        // no-op
                    }
                })
    }

    fun changePage(page: Int) {
        getArticles(query, page)
    }

}
