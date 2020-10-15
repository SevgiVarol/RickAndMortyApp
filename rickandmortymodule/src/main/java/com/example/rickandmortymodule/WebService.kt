package com.example.rickandmortymodule

import com.example.rickandmortymodule.models.CharacterListModel
import com.example.rickandmortymodule.models.CharacterModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {

    @GET("character")
    fun getCharacters(
        @Query("page") page: Int?
    ): Call<CharacterListModel>

    @GET("character/{id}")
    fun getCharacterById(
        @Path("id") id: Int
    ): Call<CharacterModel>

    companion object {
        @Volatile
        private var instance: WebService? = null

        fun getInstance(): WebService {
            return instance
                ?: synchronized(this) {
                    instance
                        ?: buildWebService("https://rickandmortyapi.com/api/").also {
                            instance = it
                        }
                }
        }

        fun clearInstance() {
            instance = null
        }

        private fun buildWebService(requestUrl: String): WebService {
            val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val client: OkHttpClient = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
                    .addInterceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("X-Requested-With", "XMLHttpRequest")
                            .build()
                        chain.proceed(newRequest)
                    }
            }.build()

            return Retrofit.Builder()
                .baseUrl(requestUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebService::class.java)
        }
    }
}