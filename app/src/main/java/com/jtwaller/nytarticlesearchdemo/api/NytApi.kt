package com.jtwaller.nytarticlesearchdemo.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NytApi {
    @GET("/svc/search/v2/articlesearch.json")
    fun getArticles(@Query("q") query: String,
                    @Query("page") page: String,
                    @Query("api-key") key: String)
            : Observable<ArticleSearchObject>
}