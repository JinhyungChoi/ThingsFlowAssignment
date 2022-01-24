package com.cjinhyung.thingsflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cjinhyung.thingsflow.network.GitDetailResponse
import com.cjinhyung.thingsflow.network.User
import com.cjinhyung.thingsflow.repository.remote.GitIssueDetailDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val gitIssueDetailDataSource: GitIssueDetailDataSource): ViewModel() {
    private var _gitDetailResponse : MutableLiveData<GitDetailResponse> = MutableLiveData()
    val gitDetailResponse: LiveData<GitDetailResponse> get() = _gitDetailResponse

    fun loadDetailIssue(org: String, repo: String, issueNum: String){
        viewModelScope.launch{
            gitIssueDetailDataSource.loadGitIssueDetail(org, repo, issueNum)?.let {
                _gitDetailResponse.value = it
            }
            println(gitDetailResponse.value!!.user)
        }
    }

}