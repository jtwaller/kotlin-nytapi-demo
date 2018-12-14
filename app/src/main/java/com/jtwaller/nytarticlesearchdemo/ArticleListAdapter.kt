package com.jtwaller.nytarticlesearchdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ArticleListAdapter(val context: Context, val articles: List<NytArticle>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View

        if (convertView == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.article_item, parent, false)
        } else {
            view = convertView
        }

        val imageView = view.findViewById<ImageView>(R.id.img_thumbnail)
        val titleView = view.findViewById<TextView>(R.id.article_title)

        Picasso.get()
                .load(getItem(position).thumbnail)
                .placeholder(R.mipmap.ic_launcher)
                .resize(50, 50)
                .centerCrop()
                .into(imageView)
        titleView.text = getItem(position).title

        return view
}

    override fun getItem(position: Int): NytArticle {
        return articles.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return articles.size
    }
}