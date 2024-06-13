package com.example.pronob_03_amad_01.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pronob_03_amad_01.R
import com.example.pronob_03_amad_01.model.Product
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class Product_Adapter(private val productList: List<Product>, private val context: Context):RecyclerView.Adapter<Product_Adapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Product_Adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: Product_Adapter.ViewHolder, position: Int) {
        val product = productList[position]
        holder.title.text = product.title
        holder.price.text = product.price.toString()
        if (product.images.isNotEmpty()) {
            Picasso.get().load(product.images[0]).into(holder.image)
        }
    }

    override fun getItemCount(): Int =productList.size

    inner class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        val title: TextView = itemView.findViewById(R.id.product_name)
        val price: TextView = itemView.findViewById(R.id.price)
        val image: CircleImageView = itemView.findViewById(R.id.profile_image)

    }

}