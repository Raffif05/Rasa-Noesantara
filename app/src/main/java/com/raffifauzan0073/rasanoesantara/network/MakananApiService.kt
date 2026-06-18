package com.raffifauzan0073.rasanoesantara.network

import com.raffifauzan0073.rasanoesantara.model.Makanan
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers


private const val BASE_URL = "https://bvuhcyzqtiqbbidgczvo.supabase.co/rest/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MakananApiService {
    @Headers(
        "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJ2dWhjeXpxdGlxYmJpZGdjenZvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODE3MTgwODUsImV4cCI6MjA5NzI5NDA4NX0.36BeBja3CQ3EY-qwZ5nrvh8tH0kHg6NO9oiG92ZvGuc",
        "Authorization: sb_publishable_6vbxeY7nXVeT94FJW44wRQ_xoH8WGvH"
    )
    @GET("Makanan")
    suspend fun getMakanan(): List<Makanan>
}

object MakananApi {
    val service: MakananApiService by lazy {
        retrofit.create(MakananApiService::class.java)
    }

}

enum class ApiStatus { LOADING, SUCCESS, FAILED }
