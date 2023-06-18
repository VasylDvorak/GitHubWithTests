package com.geekbrains.tests.di.application_modules

import com.geekbrains.tests.presenter.RepositoryContract
import com.geekbrains.tests.repository.GitHubRepository
import dagger.Module
import dagger.Provides

@Module
class GitHubModule {
    @Provides
    fun repository() : RepositoryContract = GitHubRepository()
}