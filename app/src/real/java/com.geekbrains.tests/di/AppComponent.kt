package com.geekbrains.tests.di


import com.geekbrains.tests.di.application_modules.AppModule
import com.geekbrains.tests.di.application_modules.GitHubModule
import com.geekbrains.tests.di.application_modules.RepositoryModule
import com.geekbrains.tests.presenter.search.SearchPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RepositoryModule::class,
        GitHubModule::class
    ]
)
interface AppComponent {

    fun inject(searchPresenter: SearchPresenter)
}