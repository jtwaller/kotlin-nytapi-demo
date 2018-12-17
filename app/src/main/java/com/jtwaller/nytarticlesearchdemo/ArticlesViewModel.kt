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

class ArticlesViewModel @Inject constructor(val api: NytRestApi): ViewModel() {

    var articles: MutableLiveData<List<NytArticle>> = MutableLiveData()

    init {
        getArticles("godzilla")
    }

    fun getArticles(query: String, page: Int = 0) {

        api.getArticles(query, page).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object: Observer<ArticleSearchObject> {
                    override fun onComplete() {
                        // no-op
                    }

                    override fun onSubscribe(d: Disposable) {
                        // no-op
                    }

                    override fun onNext(t: ArticleSearchObject) {
                        Log.d(MainActivity.TAG, ": next")

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

}
