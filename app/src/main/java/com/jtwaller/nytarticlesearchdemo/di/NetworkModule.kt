package com.jtwaller.nytarticlesearchdemo.di

import android.content.Context
import com.jtwaller.nytarticlesearchdemo.R
import com.jtwaller.nytarticlesearchdemo.api.NytApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideNytApi(retrofit: Retrofit) = retrofit.create(NytApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.nytimes.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(apiKeyInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            val apiKey = context.getString(R.string.nyt_api_key)

            val request = chain
                    .request()
                    .newBuilder()
                    .addHeader("api-key", apiKey)
                    .build()

            chain.proceed(request)
        }
    }

}