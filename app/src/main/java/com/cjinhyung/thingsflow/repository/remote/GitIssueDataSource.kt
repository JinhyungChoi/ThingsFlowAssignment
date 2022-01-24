package com.cjinhyung.thingsflow.repository.remote

import com.cjinhyung.thingsflow.network.GitIssueService
import com.cjinhyung.thingsflow.network.GitResponseItem
import javax.inject.Inject

class GitIssueDataSource @Inject constructor(private val gitIssueService: GitIssueService) {
    suspend fun loadGitIssue(org: String, repo: String): List<GitResponseItem> {
        val issues = mutableListOf<GitResponseItem>()
        try {
            issues.addAll(gitIssueService.loadGitIssue(org, repo))
        } catch (e: Exception){
        }
        return issues
    }
}