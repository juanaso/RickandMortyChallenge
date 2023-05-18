package com.juanasoapp.rickandmortychallenge.core

import com.jakewharton.espresso.OkHttp3IdlingResource
import com.juanasoapp.rickandmortychallenge.api.RickAndMortyAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var client = OkHttpClient()
var idlingResource = OkHttp3IdlingResource.create("okhttp", client)

@Module
@InstallIn(SingletonComponent::class)
class RickAndMortyModule {

    @Provides
    fun rickAndMortyAPI(retrofit: Retrofit) = retrofit.create(RickAndMortyAPI::class.java)

    @Provides
    fun retrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(client)
//            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}