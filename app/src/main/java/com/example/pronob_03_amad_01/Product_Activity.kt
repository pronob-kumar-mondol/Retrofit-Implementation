package com.example.pronob_03_amad_01

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pronob_03_amad_01.adapter.Product_Adapter
import com.example.pronob_03_amad_01.model.Product
import com.example.pronob_03_amad_01.service.ApiClient
import com.example.pronob_03_amad_01.service.ApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Product_Activity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var retryButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        retryButton = findViewById(R.id.fab)

        fetchProducts()

        retryButton.setOnClickListener {
            fetchProducts()
        }

    }

    private fun fetchProducts() {
        if (isNetworkAvailable()) {
            val apiService = ApiClient.getClient().create(ApiService::class.java)
            val call = apiService.getProducts()

            call.enqueue(object : Callback<List<Product>> {
                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    if (response.isSuccessful) {
                        val products = response.body()!!
                        val adapter = Product_Adapter(products, this@Product_Activity)
                        recyclerView.adapter = adapter
                    } else {
                        Log.e("Product_Activity", "Response not successful: ${response.code()}")
                        Toast.makeText(this@Product_Activity, "Failed to fetch data: ${response.message()}", Toast.LENGTH_SHORT).show()
                        retryButton.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    Log.e("Product_Activity", "Network request failed: ${t.message}")
                    Toast.makeText(this@Product_Activity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                    retryButton.visibility = View.VISIBLE
                }
            })
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            retryButton.visibility = View.VISIBLE
        }
    }

    private fun isNetworkAvailable(): Boolean {

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}