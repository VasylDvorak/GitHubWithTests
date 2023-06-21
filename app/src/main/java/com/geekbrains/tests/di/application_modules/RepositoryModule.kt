package com.geekbrains.tests.di.application_modules

import com.geekbrains.tests.presenter.RepositoryContract
import com.geekbrains.tests.repository.GitHubRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
   fun bindRepositoryContract(repo: GitHubRepository): RepositoryContract=repo
}