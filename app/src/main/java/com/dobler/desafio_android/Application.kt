package com.dobler.desafio_android

import android.app.Application
import com.dobler.desafio_android.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Application)
            modules(
                listOf(
                    AppModule.vieModelModule,
                    AppModule.apiModule,
                    AppModule.repositoriesModule
                )
            )
        }
    }
}
