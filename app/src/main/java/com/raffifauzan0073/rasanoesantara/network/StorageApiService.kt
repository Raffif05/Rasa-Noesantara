package com.raffifauzan0073.rasanoesantara.network

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface StorageApiService {

    @POST("object/makanan/{fileName}")
    suspend fun uploadImage(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String = "image/jpeg",
        @Header("x-upsert") upsert: String = "true",
        @Path("fileName") fileName: String,
        @Body image: RequestBody
    ): Response<Unit>
}