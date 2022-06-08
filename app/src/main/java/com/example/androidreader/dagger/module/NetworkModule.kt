package com.example.androidreader.dagger.module

import com.example.androidreader.data.service.BookService
import com.example.androidreader.data.service.DictionaryService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule {

    @Provides
    fun provideBookRetrofitService(): BookService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/books/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(BookService::class.java)
    }

    @Provides
    fun provideDictionaryService(): DictionaryService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(DictionaryService::class.java)
    }
}