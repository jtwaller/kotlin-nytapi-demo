package com.jtwaller.nytarticlesearchdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jtwaller.nytarticlesearchdemo.api.ArticleSearchObject
import com.jtwaller.nytarticlesearchdemo.api.NytRestApi
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    @Inject
    lateinit var nytRestApi: NytRestApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NytApp.networkComponent.inject(this)

        Log.d(TAG, ": created")

        nytRestApi.getArticles("godzilla")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object: Observer<ArticleSearchObject> {
                    override fun onComplete() {
                        Log.d(TAG, ": complete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, ": subbed")
                    }

                    override fun onNext(t: ArticleSearchObject) {
                        Log.d(TAG, ": next")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, ": error -> " + e.printStackTrace())
                    }
                })

    }

}
