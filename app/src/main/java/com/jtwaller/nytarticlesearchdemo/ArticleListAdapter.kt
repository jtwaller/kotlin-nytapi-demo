package com.jtwaller.nytarticlesearchdemo

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ArticleListAdapter(val onClick: (NytArticle) -> Unit): RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    var articles: List<NytArticle> = emptyList()

    class ArticleViewHolder(val articleItemView: View): RecyclerView.ViewHolder(articleItemView) {
        val thumbnailView: ImageView = articleItemView.findViewById(R.id.img_thumbnail)
        val titleView: TextView = articleItemView.findViewById(R.id.article_title)
    }

    fun loadItems(articleItems: List<NytArticle>) {
        articles = articleItems
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.article_item, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        Picasso.get()
                .load(articles[position].thumbnail)
                .placeholder(R.mipmap.ic_launcher)
                .resize(50, 50)
                .centerCrop()
                .into(holder.thumbnailView)

                holder.titleView.text = articles[position].title

        holder.articleItemView.setOnClickListener { onClick(articles[position]) }
    }

}