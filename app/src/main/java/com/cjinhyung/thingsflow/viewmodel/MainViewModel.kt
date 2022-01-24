package com.cjinhyung.thingsflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cjinhyung.thingsflow.network.GitResponseItem
import com.cjinhyung.thingsflow.repository.remote.GitIssueDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val gitIssueDataSource: GitIssueDataSource) :
    ViewModel() {

    private var _issues : MutableLiveData<List<GitResponseItem>> = MutableLiveData()
    val issues : LiveData<List<GitResponseItem>> get() = _issues

    fun loadGitIssue(org: String, repo: String) {
        viewModelScope.launch {
            _issues.value =  gitIssueDataSource.loadGitIssue(org, repo)
        }
    }
}