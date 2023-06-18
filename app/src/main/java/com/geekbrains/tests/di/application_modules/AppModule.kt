package com.geekbrains.tests.di.application_modules

import com.geekbrains.tests.application.App
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: App) {
    @Provides
    fun app(): App {
        return app
    }
}