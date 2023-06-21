package com.geekbrains.tests.di.application_modules

import com.geekbrains.tests.repository.GitHubRetrofit
import dagger.Module
import dagger.Provides

@Module
class GitHubModule {
    @Provides
    fun gitHubApi() = GitHubRetrofit().createRetrofit()

}