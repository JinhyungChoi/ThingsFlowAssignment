package com.cjinhyung.thingsflow.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.cjinhyung.thingsflow.R
import com.cjinhyung.thingsflow.databinding.ActivityPopupBinding
import com.cjinhyung.thingsflow.network.GitIssueService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PopupActivity : Activity() {
    private lateinit var binding : ActivityPopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_popup)
        setListener()
    }

    private fun setListener() {
        binding.btSearch.setOnClickListener {
            val org = binding.etOrg.text.toString()
            val repo = binding.etRepo.text.toString()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(GitIssueService::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    val response = service.loadGitIssue(org,repo)
                    println(response)
                    val intent = Intent()
                    intent.putExtra("org", org)
                    intent.putExtra("repo",repo)
                    setResult(RESULT_OK, intent)
                    finish()
                } catch (e: Exception){
                    setResult(RESULT_CANCELED)
                    finish()
                }
            }
        }
    }
}