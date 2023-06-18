package com.geekbrains.tests.repository

import com.geekbrains.tests.view.search.MainActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
const val BASE_URL = "https://api.github.com"

class Retrofit {
    fun createRetrofit(): GitHubApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }
}