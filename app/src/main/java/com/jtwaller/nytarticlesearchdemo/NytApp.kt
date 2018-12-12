package com.jtwaller.nytarticlesearchdemo

import android.app.Application
import com.jtwaller.nytarticlesearchdemo.di.AppModule
import com.jtwaller.nytarticlesearchdemo.di.DaggerNetworkComponent
import com.jtwaller.nytarticlesearchdemo.di.NetworkComponent

class NytApp: Application() {

    companion object {
        lateinit var networkComponent: NetworkComponent
    }

    override fun onCreate() {
        super.onCreate()
        networkComponent = DaggerNetworkComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

}