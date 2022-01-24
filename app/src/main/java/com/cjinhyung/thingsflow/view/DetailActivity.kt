package com.cjinhyung.thingsflow.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.cjinhyung.thingsflow.R
import com.cjinhyung.thingsflow.databinding.ActivityDetailBinding
import com.cjinhyung.thingsflow.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private lateinit var org: String
    private lateinit var repo: String
    private lateinit var issueNum: String

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setIssueDetail()
        loadIssue()
        setObserver()
    }

    private fun setObserver() {
        detailViewModel.gitDetailResponse.observe(this, Observer {
            Glide.with(this)
                .load(it.user.avatar_url+".png")
                .into(binding.ivUserImage)
            binding.tvUserId.text = it.user.login
            binding.tvIssueBody.text = it.body
        })
    }

    private fun loadIssue() {
        detailViewModel.loadDetailIssue(org,repo,issueNum)
    }

    private fun setIssueDetail() {
        org = intent.getStringExtra("org").toString()
        repo = intent.getStringExtra("repo").toString()
        issueNum = intent.getIntExtra("issueNum",0).toString()
        title = "#$issueNum"
    }
}