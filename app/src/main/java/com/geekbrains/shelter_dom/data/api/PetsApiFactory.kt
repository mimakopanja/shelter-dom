package com.geekbrains.shelter_dom.data.api

import com.geekbrains.shelter_dom.data.api.Tls12SocketFactory.Companion.enableTls12
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


object PetsApiFactory {
    fun create(): PetsApi =
        Retrofit.Builder()
            .baseUrl("https://10.0.2.2:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .enableTls12()
                .build())
            .build()
            .create(PetsApi::class.java)


}