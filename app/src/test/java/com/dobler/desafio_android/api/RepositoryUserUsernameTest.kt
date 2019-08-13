package com.dobler.desafio_android.api

import com.dobler.desafio_android.api.UserValidator.UsernameValidator
import com.dobler.desafio_android.data.api.GithubRepositoryResponse
import com.dobler.desafio_android.data.api.GithubService
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class RepositoryUserUsernameTest : BaseServiceTest() {


    lateinit var service: GithubService
    var serviceSource = GithubService::class.java


    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(serviceSource)

        enqueueResponse("repositories_page_1.json")

    }

    @Test
    fun whenUserNameIsCorrect_shouldReturnTrue() {
        createService()
        val results: GithubRepositoryResponse? = service.getRepositoriesPage("language:Java", "stars", 1)
            .execute().body()
        mockWebServer.takeRequest()

        results?.items?.forEach {

            assertTrue(UsernameValidator.validate(it.owner.login))
        }
    }


    @Test
    fun whenUserNameLengthIsGreaterThan39_shouldReturnFalse() {
        assertFalse(UsernameValidator.validate("a".repeat(40)))
    }

    @Test
    fun whenUserNameHaveMultipleConsecutiveHyphens_shouldReturnFalse() {
        assertFalse(UsernameValidator.validate("abcd--sasd"))
    }

    @Test
    fun whenUserNameEndWithHyphen_shouldReturnFalse() {
        assertFalse(UsernameValidator.validate("aaaaaaa--"))
    }

    @Test
    fun whenUserNameStartWithHyphen_shouldReturnFalse() {
        assertFalse(UsernameValidator.validate("--aaaaaaa"))
    }

    @Test
    fun whenUserNameContainsNonAlphanumericCharactersOrHyphens_shouldReturnFalse() {
        assertFalse(UsernameValidator.validate("aa *(&&%%[]aaaaa"))
    }

}
