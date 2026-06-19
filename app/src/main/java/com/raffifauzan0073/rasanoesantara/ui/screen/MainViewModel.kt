package com.raffifauzan0073.rasanoesantara.ui.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raffifauzan0073.rasanoesantara.BuildConfig
import com.raffifauzan0073.rasanoesantara.model.Makanan
import com.raffifauzan0073.rasanoesantara.network.ApiStatus
import com.raffifauzan0073.rasanoesantara.network.MakananApi
import com.raffifauzan0073.rasanoesantara.network.StorageApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Makanan>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)



    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING

            try {
                data.value = MakananApi.service.getMakanan(
                    "eq.$userId"
                )

                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }


    fun saveData(
        userId: String,
        nama: String,
        daerah: String,
        bitmap: Bitmap
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            try {

                val apiKey = BuildConfig.SUPABASE_KEY

                val fileName =
                    "makanan_${System.currentTimeMillis()}.jpg"

                val uploadResult =
                    StorageApi.service.uploadImage(
                        apiKey = apiKey,
                        authorization = "Bearer $apiKey",
                        fileName = fileName,
                        image = bitmap.toRequestBody()
                    )

                if (!uploadResult.isSuccessful) {

                    val errorBody =
                        uploadResult.errorBody()?.string()

                    throw Exception(
                        "Upload gagal: ${uploadResult.code()} $errorBody"
                    )
                }

                val imageUrl =
                    BuildConfig.SUPABASE_BASE_URL +
                            "storage/v1/object/public/makanan/$fileName"

                val insertResult =
                    MakananApi.service.postMakanan(
                        apiKey = apiKey,
                        authorization = "Bearer $apiKey",
                        makanan = Makanan(
                            userId = userId,
                            nama = nama,
                            daerah = daerah,
                            imageUrl = imageUrl
                        )
                    )

                if (!insertResult.isSuccessful) {

                    val errorBody =
                        insertResult.errorBody()?.string()

                    throw Exception(
                        "Insert gagal: ${insertResult.code()} $errorBody"
                    )
                }

                retrieveData(userId)

            } catch (e: Exception) {

                Log.d(
                    "MainViewModel",
                    "Failure: ${e.message}"
                )

                errorMessage.value =
                    "Error: ${e.message}"
            }
        }
    }

    fun deleteData(
        userId: String,
        id: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            try {

                val apiKey = BuildConfig.SUPABASE_KEY

                val result =
                    MakananApi.service.deleteMakanan(
                        apiKey = apiKey,
                        authorization = "Bearer $apiKey",
                        id = "eq.$id"
                    )

                if (!result.isSuccessful) {
                    throw Exception(
                        "Delete gagal: ${result.code()}"
                    )
                }

                retrieveData(userId)

            } catch (e: Exception) {

                Log.d(
                    "MainViewModel",
                    "Failure: ${e.message}"
                )

                errorMessage.value =
                    "Error: ${e.message}"
            }
        }
    }

    fun updateData(
        id: Long,
        userId: String,
        nama: String,
        daerah: String,
        imageUrl: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            try {

                val apiKey = BuildConfig.SUPABASE_KEY

                val result =
                    MakananApi.service.updateMakanan(
                        apiKey = apiKey,
                        authorization = "Bearer $apiKey",
                        id = "eq.$id",
                        makanan = Makanan(
                            id = id,
                            userId = userId,
                            nama = nama,
                            daerah = daerah,
                            imageUrl = imageUrl
                        )
                    )

                if (!result.isSuccessful) {
                    throw Exception(
                        "Update gagal: ${result.code()}"
                    )
                }

                retrieveData(userId)

            } catch (e: Exception) {

                errorMessage.value =
                    "Error: ${e.message}"
            }
        }
    }

    private fun Bitmap.toRequestBody() =
        ByteArrayOutputStream().use { stream ->

            compress(
                Bitmap.CompressFormat.JPEG,
                80,
                stream
            )

            val byteArray = stream.toByteArray()

            byteArray.toRequestBody(
                "image/jpeg".toMediaTypeOrNull(),
                0,
                byteArray.size
            )
        }

    fun clearMessage() { errorMessage.value = null }
}
