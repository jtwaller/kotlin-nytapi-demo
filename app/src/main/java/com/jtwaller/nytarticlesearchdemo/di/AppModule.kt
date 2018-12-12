package com.jtwaller.nytarticlesearchdemo.di

import android.app.Application
import android.content.Context
import com.jtwaller.nytarticlesearchdemo.NytApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: NytApp) {

    @Provides
    @Singleton
    fun provideApp(): Application = app

    @Provides
    @Singleton
    fun provideContext(): Context = app

}