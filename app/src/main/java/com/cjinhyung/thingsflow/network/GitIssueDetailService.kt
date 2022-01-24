package com.cjinhyung.thingsflow.network

import retrofit2.http.GET
import retrofit2.http.Path

interface GitIssueDetailService {
    @GET("repos/{org}/{repo}/issues/{issueNum}")
    suspend fun loadGitIssueDetail(@Path("org") org: String, @Path("repo") repo: String, @Path("issueNum") issueNum: String): GitDetailResponse
}