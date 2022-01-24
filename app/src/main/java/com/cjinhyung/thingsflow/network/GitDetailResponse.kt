package com.cjinhyung.thingsflow.network

data class GitDetailResponse(
    val body: String,
    val number: Int,
    val title: String,
    val user: User
)