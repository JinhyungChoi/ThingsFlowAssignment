package com.cjinhyung.thingsflow.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.cjinhyung.thingsflow.R
import com.cjinhyung.thingsflow.databinding.ActivityMainBinding
import com.cjinhyung.thingsflow.network.GitResponseItem
import com.cjinhyung.thingsflow.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var org: String
    private lateinit var repo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setAdapter()
        setObserver()
        setListener()
    }

    private fun setListener() {
        binding.tvSearch.setOnClickListener {
            val intent = Intent(applicationContext, PopupActivity::class.java)
            popupCallback.launch(intent)
        }
    }

    private val popupCallback =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val data = it.data!!
                binding.tvSearch.text =
                    data.getStringExtra("org") + "/" + data.getStringExtra("repo")
                org = data.getStringExtra("org")!!
                repo = data.getStringExtra("repo")!!
                loadIssue(org, repo)
            } else {
                Toast.makeText(applicationContext, "없는 레포 입니다.", Toast.LENGTH_SHORT).show()
            }
        }

    private fun setObserver() {
        mainViewModel.issues.observe(this, Observer {
            adapter.issues = it as MutableList<GitResponseItem>
            adapter.notifyDataSetChanged()
        })
    }

    private fun loadIssue(org: String, repo: String) {
        mainViewModel.loadGitIssue(org, repo)
    }

    private fun setAdapter() {
        adapter = MainAdapter()
        binding.rvGitIssue.adapter = adapter
        adapter.setOnItemClickListener(object : MainAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                if (position == 5) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.setData(Uri.parse("https://thingsflow.com/ko/home"))
                    startActivity(intent)
                } else {
                    val intent = Intent(applicationContext, DetailActivity::class.java).apply {
                        putExtra("org", org)
                        putExtra("repo", repo)
                        putExtra("issueNum", adapter.issues[position].number)
                    }
                    startActivity(intent)
                }
            }
        })
    }
}