package com.cjinhyung.thingsflow.network

import retrofit2.http.GET
import retrofit2.http.Path

interface GitIssueService {

    @GET("repos/{org}/{repo}/issues")
    suspend fun loadGitIssue(@Path("org") org: String, @Path("repo") repo: String): GitResponse
}