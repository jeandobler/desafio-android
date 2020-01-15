package com.dobler.desafio_android.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.dobler.desafio_android.BuildConfig
import com.dobler.desafio_android.data.api.GithubService
import com.dobler.desafio_android.data.dao.AppDatabase
import com.dobler.desafio_android.repository.githubRepository.GithubRepository
import com.dobler.desafio_android.repository.pullRequest.PullRequestRepository
import com.dobler.desafio_android.ui.listRepository.ListRepositoryViewModel
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
            initHttpBuilder()
        }

        single {
            initRetrofit(get())
        }

        single {
            get<Retrofit>().create(GithubService::class.java) as GithubService
        }


    }

    val databaseModule = module {

        single {
            loadAppDatabase(get())
        }

    }

    val repositoriesModule = module {
        single { PullRequestRepository(get()) }
        single { GithubRepository(get(), get()) }
    }

    val vieModelModule = module {

        viewModel { ListRepositoryViewModel(get()) }
        viewModel { PullRequestViewModel(get()) }
    }

    fun initHttpBuilder(): OkHttpClient.Builder {
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
        return httpBuilder
    }

    private fun initRetrofit(httpBuilder: OkHttpClient.Builder, url: String? = null): Retrofit {

        val rightUrl = if (url.isNullOrEmpty()) BuildConfig.API_URL else url
        val retrofit = Retrofit.Builder()
            .baseUrl(rightUrl)
            .client(httpBuilder.build())

            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        return retrofit.build()
    }

    private fun loadAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "github"
        ).build()
    }
}
