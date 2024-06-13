package com.example.pronob_03_amad_01.service


import com.example.pronob_03_amad_01.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    fun getProducts(): Call<List<Product>>
}