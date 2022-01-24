package com.cjinhyung.thingsflow.repository.remote

import com.cjinhyung.thingsflow.network.GitDetailResponse
import com.cjinhyung.thingsflow.network.GitIssueDetailService
import javax.inject.Inject

class GitIssueDetailDataSource @Inject constructor(private val gitIssueDetailService: GitIssueDetailService) {
    suspend fun loadGitIssueDetail(org: String, repo: String, issueNum: String): GitDetailResponse? {
        var result : GitDetailResponse? = null
        try {
            result = gitIssueDetailService.loadGitIssueDetail(org,repo,issueNum)
        } catch(e: Exception){
            println("----------------------")
            println(e.message)
        }
        return result
    }
}