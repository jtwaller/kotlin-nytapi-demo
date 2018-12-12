package com.jtwaller.nytarticlesearchdemo.api

import io.reactivex.Observable
import javax.inject.Inject

class NytRestApi @Inject constructor(private val nytApi: NytApi) {
    fun getArticles(query: String, page: Int = 0): Observable<ArticleSearchObject> {
        return nytApi.getArticles(query, page.toString())
    }
}