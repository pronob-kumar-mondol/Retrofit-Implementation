package com.example.pronob_03_amad_01.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit:Retrofit?=null

    fun getClient(): Retrofit{
        if (retrofit==null){
            retrofit=Retrofit.Builder()
                .baseUrl("https://api.escuelajs.co/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}