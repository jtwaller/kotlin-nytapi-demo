package com.jtwaller.nytarticlesearchdemo.api

import com.google.gson.annotations.SerializedName

class ArticleSearchObject(
        val status: String,
        val response: ArticleSearchResponse
)

class ArticleSearchResponse(
        val docs: List<ArticleSearchDoc>
)

class ArticleSearchDoc(
        @SerializedName("web_url") val webUrl: String,
        @SerializedName("pub_date") val pubDate: String,
        val headline: ArticleSearchHeadline,
        val multimedia: List<ArticleSearchMultimedia>
)

class ArticleSearchHeadline(
        val main: String
)

class ArticleSearchMultimedia(
        val type: String,
        val subtype: String,
        val url: String?
)