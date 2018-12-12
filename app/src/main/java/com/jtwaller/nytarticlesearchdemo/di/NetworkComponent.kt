package com.jtwaller.nytarticlesearchdemo.di

import com.jtwaller.nytarticlesearchdemo.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface NetworkComponent {
    fun inject(mainActivity: MainActivity)
}