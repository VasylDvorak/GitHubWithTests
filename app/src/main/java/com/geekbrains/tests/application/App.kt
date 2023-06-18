package com.geekbrains.tests.application

import android.app.Application
import com.geekbrains.tests.di.DaggerAppComponent
import com.geekbrains.tests.di.AppComponent
import com.geekbrains.tests.di.application_modules.AppModule


class App : Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
