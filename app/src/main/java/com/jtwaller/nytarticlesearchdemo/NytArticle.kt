package com.jtwaller.nytarticlesearchdemo

import android.media.Image

data class NytArticle(
        val thumbnail: Image,
        val title: String,
        val url: String
)