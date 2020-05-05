package com.hem.kotlinrecyclerview

import okhttp3.OkHttpClient
import okhttp3.Request

object RestClient {
    private val okHttpClient = OkHttpClient()
    private val request = Request.Builder()
        .url("https://api.androidhive.info/json/contacts.json")
        .build()
    val api = okHttpClient.newCall(request)

}