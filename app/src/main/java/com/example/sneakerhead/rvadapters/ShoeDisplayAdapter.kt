package com.example.sneakerhead.rvadapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sneakerhead.Models.ShoeDisplayModel
import com.example.sneakerhead.R

class ShoeDisplayAdapter(val context: Context, val list: List<ShoeDisplayModel>,val productClickInterface:ProductOnClickInterface):RecyclerView.Adapter<ShoeDisplayAdapter.MyViewHolders>() {


    inner class MyViewHolders(itemView: View):RecyclerView.ViewHolder(itemView)
    {    val img=itemView.findViewById<ImageView>(R.id.ivShoeDisplayItem)
        val tvNameShoeDisplay=itemView.findViewById<TextView>(R.id.tvNameShoeDisplayItem)
        val tvPriceShoeDisplay=itemView.findViewById<TextView>(R.id.tvPriceShoeDisplayItem)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolders {
      return MyViewHolders(LayoutInflater.from(parent.context).inflate(R.layout.shoedisplaymain_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolders, position: Int) {
     holder.tvNameShoeDisplay.text="${list[position].brand} \t ${list[position].name}"
    holder.tvPriceShoeDisplay.text="${list[position].price}"
        Glide.with(context).load(list[position].imageUrl).into(holder.img)
        holder.itemView.setOnClickListener {
            productClickInterface.onCLickProduct(list[position])
        }
    }



}
interface ProductOnClickInterface {
    fun onCLickProduct(item:ShoeDisplayModel)
}