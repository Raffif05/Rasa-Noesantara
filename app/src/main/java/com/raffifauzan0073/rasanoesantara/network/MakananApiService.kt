package com.raffifauzan0073.rasanoesantara.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers


private const val BASE_URL = "https://bvuhcyzqtiqbbidgczvo.supabase.co/rest/v1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MakananApiService {
    @Headers(
        "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJ2dWhjeXpxdGlxYmJpZGdjenZvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODE3MTgwODUsImV4cCI6MjA5NzI5NDA4NX0.36BeBja3CQ3EY-qwZ5nrvh8tH0kHg6NO9oiG92ZvGuc",
        "Authorization: sb_publishable_6vbxeY7nXVeT94FJW44wRQ_xoH8WGvH"
    )
    @GET("Makanan")
    suspend fun getMakanan(): String
}

object MakananApi {
    val service: MakananApiService by lazy {
        retrofit.create(MakananApiService::class.java)
    }
}