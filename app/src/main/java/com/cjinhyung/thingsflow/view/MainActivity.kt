package com.cjinhyung.thingsflow.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.cjinhyung.thingsflow.R
import com.cjinhyung.thingsflow.databinding.ActivityMainBinding
import com.cjinhyung.thingsflow.network.GitResponseItem
import com.cjinhyung.thingsflow.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setAdapter()
        loadIssue()
        setObserver()
    }

    private fun setObserver() {
        mainViewModel.issues.observe(this, Observer {
            adapter.issues = it as MutableList<GitResponseItem>
            adapter.notifyDataSetChanged()
        })
    }

    private fun loadIssue() {
        mainViewModel.loadGitIssue("google", "dagger")
    }

    private fun setAdapter() {
        adapter = MainAdapter()
        binding.rvGitIssue.adapter = adapter
        adapter.setOnItemClickListener(object: MainAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                if (position == 5){
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.setData(Uri.parse("https://thingsflow.com/ko/home"))
                    startActivity(intent)
                } else {
                    val intent = Intent(applicationContext, DetailActivity::class.java).apply{
                        putExtra("org", "google")
                        putExtra("repo", "dagger")
                        putExtra("issueNum", adapter.issues[position].number)
                    }
                    startActivity(intent)
                }
            }
        })
    }
}