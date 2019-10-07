package com.dobler.desafio_android.di

import android.util.Log
import com.dobler.desafio_android.BuildConfig
import com.dobler.desafio_android.data.api.GithubService
import com.dobler.desafio_android.data.repository.githubRepository.GithubRepository
import com.dobler.desafio_android.data.repository.pullRequest.PullRequestRepository
import com.dobler.desafio_android.ui.githubRepository.ListRepositoryViewModel
import com.dobler.desafio_android.ui.pull.PullRequestViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppModule {

    val apiModule = module {
        single {
            initRetrofit()
        }

        single {
            get<Retrofit>().create(GithubService::class.java) as GithubService
        }
    }

    val repositoriesModule = module {
        single { PullRequestRepository(get()) }
        single { GithubRepository(get()) }
    }

    val vieModelModule = module {

        viewModel { ListRepositoryViewModel(get()) }
        viewModel { PullRequestViewModel(get()) }
    }

    private fun initRetrofit(): Retrofit {
        val httpBuilder = OkHttpClient.Builder()

        httpBuilder.readTimeout(15, TimeUnit.SECONDS)
        httpBuilder.connectTimeout(15, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.d("RETROFIT", message)
            })
            logging.level = HttpLoggingInterceptor.Level.BODY

            httpBuilder.addInterceptor(logging)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(httpBuilder.build())

            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        return retrofit.build()
    }
}
