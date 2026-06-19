package com.raffifauzan0073.rasanoesantara.network

import retrofit2.Retrofit

private const val STORAGE_URL =
    "https://bvuhcyzqtiqbbidgczvo.supabase.co/storage/v1/"

object StorageApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl(STORAGE_URL)
        .build()

    val service: StorageApiService by lazy {
        retrofit.create(StorageApiService::class.java)
    }
}