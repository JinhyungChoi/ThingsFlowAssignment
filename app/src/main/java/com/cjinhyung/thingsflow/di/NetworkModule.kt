package com.cjinhyung.thingsflow.di

import com.cjinhyung.thingsflow.network.GitIssueDetailService
import com.cjinhyung.thingsflow.network.GitIssueService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val baseUrl = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideGitIssue(): GitIssueService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(GitIssueService::class.java)
    }

    @Provides
    @Singleton
    fun provideGitIssueDetail(): GitIssueDetailService{
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(GitIssueDetailService::class.java)
    }
}