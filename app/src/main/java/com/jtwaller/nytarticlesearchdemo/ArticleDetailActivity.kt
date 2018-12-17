package com.jtwaller.nytarticlesearchdemo

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class ArticleDetailActivity: AppCompatActivity() {

    private lateinit var mShareActionProvider: ShareActionProvider
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_detail_activity)

        url = intent.getStringExtra("url")
        val webView = findViewById<WebView>(R.id.webview)

        webView.webViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }
        }

        webView.loadUrl(url)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.share_menu, menu)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }

        val item = menu.findItem(R.id.menu_item_share)
        mShareActionProvider = MenuItemCompat.getActionProvider(item) as ShareActionProvider
        mShareActionProvider.setShareIntent(shareIntent)

        return true
    }
}